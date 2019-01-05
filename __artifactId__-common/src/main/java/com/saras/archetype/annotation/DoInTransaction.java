package com.saras.archetype.annotation;

import java.lang.annotation.*;

/**
 * description:编程式事务注解
 * saras_xu@163.com 2017-03-01 12:31 创建
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DoInTransaction {
}
