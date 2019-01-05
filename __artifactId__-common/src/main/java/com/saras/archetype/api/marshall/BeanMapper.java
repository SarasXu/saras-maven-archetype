/**
 * Copyright (c) 2005-2012 springside.org.cn
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.saras.archetype.api.marshall;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.saras.archetype.utils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;

import java.util.*;

/**
 * 简单封装Dozer, 实现深度转换Bean<->Bean的Mapper.实现:
 * <p>
 * 1. 持有Mapper的单例. 2. 返回值类型转换. 3. 批量转换Collection中的所有对象. 4.
 * 区分创建新的B对象与将对象A值复制到已存在的B对象两种函数.
 *
 * @author calvin
 */
public class BeanMapper {

    /**
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * 基于Dozer转换对象的类型.
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    /**
     * 将属性值设入指定对象中
     *
     * @param source
     * @param dest
     * @param <T>
     */
    public static <T> void map(Object source, T dest) {
        dozer.map(source, dest);
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> mapList(Collection sourceList,
                                      Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    /**
     * 基于Dozer将对象A的值拷贝到对象B中.
     */
    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }

    public static DozerBeanMapper getDozer() {
        return dozer;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map<String, Object> deepMap(Object source, String[] properties)
            throws Exception {

        Map<String, Object> map = Maps.newHashMap();
        for (String property : properties) {
            if (StringUtils.contains(property, ".")) {
                String currentProperty = StringUtils.substringBefore(property,
                        ".");
                String remainProperties = StringUtils.substringAfter(property,
                        ".");
                Map<String, Object> remainMap = deepMap(
                        BeanUtils.getDeclaredProperty(source, currentProperty),
                        new String[]{remainProperties});
                if (map.get(currentProperty) != null) {
                    ((Map) map.get(currentProperty)).putAll(remainMap);
                } else {
                    map.put(currentProperty, remainMap);
                }

            } else {
                Object value = BeanUtils.getDeclaredProperty(source, property);
                map.put(property, value);
            }
        }
        return map;
    }

    public static void main(String[] args) throws Exception {

        Set<Role> roles = Sets.newHashSet();
        for (int i = 1; i < 4; i++) {
            roles.add(new Role(i, "ROLE_" + i));
        }
        User user = new User(roles);

        System.out.println(deepMap(user, new String[]{"id", "name", "emp.id",
                "emp.realName", "roles#name"}));

    }

    public static class User {
        private int id = 1;
        private String name = "zh";
        private Date birthday = new Date();

        private Emp emp = new Emp();
        private Set<Role> roles;

        public User(Set<Role> roles) {
            super();
            this.roles = roles;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public Emp getEmp() {
            return emp;
        }

        public void setEmp(Emp emp) {
            this.emp = emp;
        }

        public Set<Role> getRoles() {
            return roles;
        }

        public void setRoles(Set<Role> roles) {
            this.roles = roles;
        }

    }

    public static class Emp {
        private int id = 1;
        private String realName = "zhang";

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

    }

    public static class Role {
        private int id = 1;
        private String name;

        public Role(int id, String name) {
            super();
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}