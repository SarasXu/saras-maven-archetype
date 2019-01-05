package com.saras.archetype.utils;

import com.google.common.collect.Sets;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by zhanghao on 2017/9/7.
 */
public class ReflectionUtils {


    /**
     * 获取clazz 的所有字段 包含继承的，，
     *
     * @param clazz
     * @return
     */
    public static Set getAllFiled(Class<?> clazz) {

        Set<Field> fieldSets = Sets.newHashSet();

        for (Class classType = clazz; classType != null && classType != Object.class; classType = classType
                .getSuperclass()) {
            Field[] fields = classType.getDeclaredFields();
            fieldSets.addAll(Sets.newHashSet(fields));
        }

        return fieldSets;
    }

    /**
     * 获取object 的所有字段 包含继承的，，
     *
     * @param object
     * @return
     */
    public static Set getAllFiled(Object object) {
        Assert.notNull(object, "object 不能为空");

        Class<?> clazz = object.getClass();

        Set<Field> fieldSets = Sets.newHashSet();
        for (Class classType = clazz; classType != null && classType != Object.class; classType = classType
                .getSuperclass()) {
            Field[] fields = classType.getDeclaredFields();
            fieldSets.addAll(Sets.newHashSet(fields));
        }

        return fieldSets;
    }


}
