package com.saras.archetype.enums;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * description:通用型枚举，表示开启(YES)和关闭(NO)，表示boolean类型的状态
 * saras_xu@163.com 2017-05-02 09:07 创建
 */
public enum SwitchEnum {
    /**
     * 开启/YES
     */
    OPEN("OPEN", "开启"),

    /**
     * 关闭/NO
     */
    CLOSE("CLOSE", "关闭");

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
    private SwitchEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取全部枚举
     */
    public static List<SwitchEnum> getAllEnum() {
        List<SwitchEnum> list = Lists.newArrayList();
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
        for (SwitchEnum each : values()) {
            list.add(each.code());
        }
        return list;
    }

    /**
     * 通过Code获取枚举
     */
    public static SwitchEnum getEnumByCode(String code) {
        SwitchEnum result = null;
        for (SwitchEnum statusEnum : getAllEnum()) {
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
