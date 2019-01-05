package com.saras.archetype.api.verify;

import com.saras.archetype.utils.Reflections;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Signatures {
    private static final Logger logger = LoggerFactory.getLogger(Signatures.class);

    public String sign(Object object, String secretKey) {
        Set<String> simplePropertyNames = Reflections.getSimpleFieldNames(object.getClass());
        String[] propertyNames = simplePropertyNames.toArray(new String[]{});

        List<String> pairNameValues = new ArrayList<>(propertyNames.length);
        String value;
        for (String name : propertyNames) {
            value = (String) Reflections.invokeGetter(object, name);
            if (StringUtils.isNotBlank(value) && !StringUtils.equals(name, "sign")) {
                pairNameValues.add(name + "=" + value);
            }
        }
        Collections.sort(pairNameValues);
        return sign(pairNameValues, secretKey);
    }

    public String sign(List<String> parameters, String secretKey) {
        String stringToSign = StringUtils.join(parameters.iterator(), "&");
        String signature = DigestUtils.md5Hex(stringToSign + secretKey);
        logger.info("待签名字符串:" + stringToSign + ", 签名结果:" + signature);
        return signature;
    }

    /**
     * 对Map中的k-v串做签名
     *
     * @param formData
     * @param secretKey
     * @param ignoreBlankValue 是否忽略值为空的属性
     * @param ignoreProperties 忽略的属性
     * @return
     */
    public String sign(Map<String, String> formData, String secretKey, boolean ignoreBlankValue,
                       List<String> ignoreProperties) {
        Map<String, String> sortedMap = new TreeMap<>(formData);
        if (sortedMap.containsKey("sign")) {
            sortedMap.remove("sign");
        }
        StringBuilder stringToSign = new StringBuilder();
        if (sortedMap.size() > 0) {
            for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
                if (!ignoreProperties.contains(entry.getKey())) {
                    if (ignoreBlankValue) {
                        if (StringUtils.isNotBlank(entry.getValue())) {
                            stringToSign.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                        }
                    } else {
                        stringToSign.append(entry.getKey()).append("=").append("").append("&");
                    }
                }
            }
            stringToSign.deleteCharAt(stringToSign.length() - 1);
        }
        stringToSign.append(secretKey);
        String signature = DigestUtils.md5Hex(stringToSign.toString());
        logger.info("待签名字符串:" + stringToSign + ",签名结果:" + signature);
        return signature;
    }

}
