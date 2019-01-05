package com.saras.archetype.api.annotation;

import com.saras.archetype.api.enums.RequestTypeEnum;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:27 创建
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface ApiServiceBean {
    /**
     * 服务名
     *
     * @return
     */
    String serviceName();

    /**
     * 版本号,默认1.0
     *
     * @return
     */
    String version() default "1.0";

    /**
     * 服务描述
     *
     * @return
     */
    String description();

    /**
     * 请求类型
     *
     * @return
     */
    RequestTypeEnum requestTYpe();
}
