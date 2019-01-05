package com.saras.archetype.enums;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * @author hongsir
 * @apiNote 系统角色枚举
 * @date 2018/3/14 下午2:07
 */
public enum SystemRoleEnum {
    SYSTEM_ADMIN("SYSTEM_ADMIN", "系统管理员"),
    COMPANY_ADMIN("COMPANY_ADMIN", "企业管理员"),
    COMPANY_USER("COMPANY_USER", "企业用户");

    /**
     * 枚举值码
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String message;

    /**
     * @param code    枚举值码。
     * @param message 枚举描述。
     */
    private SystemRoleEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取全部枚举
     */
    public static List<SystemRoleEnum> getAllEnum() {
        List<SystemRoleEnum> list = Lists.newArrayList();
        list.addAll(Arrays.asList(values()));
        return list;
    }

    /**
     * 获取全部枚举值
     *
     * @return List<String>
     */
    public static List<String> getAllEnumCode() {
        List<String> list = Lists.newArrayList();
        for (SystemRoleEnum each : values()) {
            list.add(each.code());
        }
        return list;
    }

    /**
     * 通过Code获取枚举
     */
    public static SystemRoleEnum getEnumByCode(String code) {
        SystemRoleEnum result = null;
        for (SystemRoleEnum statusEnum : getAllEnum()) {
            if (statusEnum.code.equals(code)) {
                result = statusEnum;
                break;
            }
        }
        return result;
    }

    /**
     * 得到枚举值码。
     *
     * @return 枚举值码。
     */
    public String code() {
        return code;
    }

    /**
     * 得到枚举描述。
     *
     * @return 枚举描述。
     */
    public String message() {
        return message;
    }
}
