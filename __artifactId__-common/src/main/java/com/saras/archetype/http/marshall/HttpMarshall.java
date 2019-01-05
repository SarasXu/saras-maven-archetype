package com.saras.archetype.http.marshall;

import com.google.common.collect.Maps;
import com.saras.archetype.api.marshall.JsonUtil;
import com.saras.archetype.api.verify.Signatures;
import com.saras.archetype.utils.Reflections;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:27 创建
 */
@Component
public class HttpMarshall {
    private final static Logger logger = LoggerFactory.getLogger(HttpMarshall.class);
    @Autowired
    private Signatures signatures;

    public Map<String, String> marshall(Object object, String secretKey, boolean collectEmptyProperty,
                                        List<String> ignorePropertyList) {
        try {
            WaitToSignResult result = parseAndSortRequest(object, collectEmptyProperty, ignorePropertyList);
            String sign = signatures.sign(result.getPropertyValueList(), secretKey);
            Map<String, String> map = Maps.newTreeMap();
            for (String pair : result.getPropertyValueList()) {
                int index = pair.indexOf("=");
                map.put(pair.substring(0, index), pair.substring(index + 1));
            }
            //若需要收集空值,则将空值放到map中
            if (collectEmptyProperty) {
                map.putAll(result.getEmptyPropertyList());
            }
            //将签名和其他忽略的属性放到map中
            map.put("sign", sign);
            for (String ignoreProperty : ignorePropertyList) {
                if (!StringUtils.equals(ignoreProperty, "sign")) {
                    map.put(ignoreProperty, toValue(Reflections.invokeGetter(object, ignoreProperty)));
                }
            }
            return map;
        } catch (Exception e) {
            logger.error("组装http报文时发生异常", e);
            throw new RuntimeException("组装http报文时发生异常");
        }
    }

    public Map<String, String> objectToPropertyMap(Object object) {
        if (object == null) {
            return null;
        } else {
            Map<String, String> returnMap = new HashMap<>();
            Set<String> simplePropertyNames = Reflections.getFieldNames(object.getClass());
            String[] propertyNames = simplePropertyNames.toArray(new String[]{});

            String value;
            for (String name : propertyNames) {
                Object obj = Reflections.invokeGetter(object, name);
                value = toValue(obj);
                returnMap.put(name, value);
            }
            return returnMap;
        }
    }

    /**
     * 生成待签名字符串
     *
     * @param request
     * @param ignorePropertyList
     * @return
     */
    private WaitToSignResult parseAndSortRequest(Object request, boolean collectEmptyProperty,
                                                 List<String> ignorePropertyList) {
        WaitToSignResult result = new WaitToSignResult();
        Set<String> simplePropertyNames = Reflections.getFieldNames(request.getClass());
        String[] propertyNames = simplePropertyNames.toArray(new String[]{});

        List<String> pairNameValues = new ArrayList<String>(propertyNames.length);
        Map<String, String> emptyStrMap = new HashMap<>();
        String value;
        for (String name : propertyNames) {
            if (ignorePropertyList.contains(name)) {
                continue;
            }
            Object obj = Reflections.invokeGetter(request, name);
            value = toValue(obj);

            if (StringUtils.isNotBlank(value)) {
                pairNameValues.add(name + "=" + value);
            } else if (collectEmptyProperty) {
                emptyStrMap.put(name, "");
            }
        }
        Collections.sort(pairNameValues);

        result.setEmptyPropertyList(emptyStrMap);
        result.setPropertyValueList(pairNameValues);
        return result;
    }

    private String toValue(Object object) {
        if (object == null) {
            return "";
        }

        if (isPrimitiveAndStringType(object)) {
            return object.toString();
        }

        return JsonUtil.toJsonString(object);
    }

    private boolean isPrimitiveAndStringType(Object obj) {
        return obj.getClass().isPrimitive() || obj instanceof String;
    }

    private static class WaitToSignResult {
        List<String> propertyValueList;
        Map<String, String> emptyPropertyList;

        public List<String> getPropertyValueList() {
            return propertyValueList;
        }

        public void setPropertyValueList(List<String> propertyValueList) {
            this.propertyValueList = propertyValueList;
        }

        public Map<String, String> getEmptyPropertyList() {
            return emptyPropertyList;
        }

        public void setEmptyPropertyList(Map<String, String> emptyPropertyList) {
            this.emptyPropertyList = emptyPropertyList;
        }
    }
}
