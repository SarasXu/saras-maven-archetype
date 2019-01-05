package com.saras.archetype.http.marshall;

import com.saras.archetype.api.marshall.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:27 创建
 */
@Component
public class HttpUnMarshall {
    private final static Logger logger = LoggerFactory.getLogger(HttpUnMarshall.class);

    /**
     * Map对象转换成指定类型对象
     *
     * @param data
     * @param targetClass
     * @param <T>
     * @return
     */
    public <T> T unMarshall(Map<String, String> data, Class<T> targetClass) {
        try {
            T target = targetClass.newInstance();
            BeanMapper.map(data, target);
            return target;
        } catch (Exception e) {
            logger.error("转换成指定对象时出现错误", e);
            return null;
        }
    }

    public void unMarshall(Map<String, String> data, Object target) {
        try {
            BeanMapper.map(data, target);
        } catch (Exception e) {
            logger.error("转换成指定对象时出现错误", e);
            throw new IllegalArgumentException("转换对象出现错误");
        }
    }

}
