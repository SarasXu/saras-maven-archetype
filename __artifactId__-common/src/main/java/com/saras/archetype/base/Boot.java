package com.saras.archetype.base;

import com.saras.archetype.annotation.AppBoot;
import org.springframework.boot.SpringApplication;

/**
 * message:项目启动类
 * saras_xu@163.com 2017-03-10 10:50 创建
 */
public class Boot {

    public static void run(Class clazz, String... args) {
        long begin = System.currentTimeMillis();
        AppBoot boot = (AppBoot) clazz.getAnnotation(AppBoot.class);
        Apps.setProfileIfNotExists(boot.env());
        Apps.setPort(String.valueOf(boot.port()));
        SpringApplication.run(clazz, args);
        long end = System.currentTimeMillis();
        System.out.println("********************************************************");
        long time = end - begin;
        System.out.println("启动成功[port：" + boot.port() + "   profile：" + Apps.getProfile() + "   in:" + time + "ms]");
        System.out.println("当前运行系统:" + System.getProperty("os.name"));
        System.out.println("********************************************************");
    }
}
