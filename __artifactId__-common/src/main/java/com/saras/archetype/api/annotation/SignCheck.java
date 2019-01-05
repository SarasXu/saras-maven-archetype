package com.saras.archetype.api.annotation;

import java.lang.annotation.*;

/**
 * description:
 *
 * @author saras_xu@163.com
 * @date 2018-01-18 16:52 创建
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SignCheck {
    /**
     * 是否api checke 默认true
     *
     * @return
     */
    boolean isApiCheck() default true;
}
