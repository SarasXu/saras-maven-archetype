package com.saras.archetype.base;

import org.springframework.util.StringUtils;

/**
 * description: 项目环境配置
 * saras_xu@163.com 2017-03-10 10:19 创建
 */
public class Apps {

    private final static String SPRING_PROFILE_ACTIVE = "spring.profiles.active";
    private final static String SERVER_PORT = "server.port";

    public static void setProfileIfNotExists(String profile) {
        if (!StringUtils.hasLength(System.getProperty(SPRING_PROFILE_ACTIVE))
                && !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {
            System.setProperty(SPRING_PROFILE_ACTIVE, profile);
        }
    }

    public static void setPort(String port) {
        if (!StringUtils.hasLength(System.getProperty(SERVER_PORT))
                && !System.getenv().containsKey("SERVER_PORT")) {
            System.setProperty(SERVER_PORT, port);
        }
    }

    public static String getProfile() {
        return System.getProperty(SPRING_PROFILE_ACTIVE);
    }
}
