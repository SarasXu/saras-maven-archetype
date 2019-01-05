package com.saras.archetype.enums;

/**
 * message:
 * saras_xu@163.com 2017-03-01 13:16 创建
 */
public enum Status {
    /**
     * 成功
     */
    SUCCESS("success", "成功"),

    /**
     * 失败
     */
    FAIL("fail", "失败"),

    /**
     * 处理中
     */
    PROCESSING("processing", "处理中");

    /**
     * 枚举值码
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String message;

    /**
     * 构建一个 Status 。
     *
     * @param code    枚举值码。
     * @param message 枚举描述。
     */
    private Status(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过枚举值码查找枚举值。
     *
     * @param code 查找枚举值的枚举值码。
     * @return 枚举值码对应的枚举值。
     * @throws IllegalArgumentException 如果 code 没有对应的 Status 。
     */
    public static Status findStatus(String code) {
        for (Status status : values()) {
            if (status.code().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("ResultInfo Status not legal:" + code);
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
