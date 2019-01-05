package com.saras.archetype.enums;

/**
 * description: 操作结果返回
 * wangzhihuan 2017-05-15 15:13 创建
 */
public enum OperationStatus {
    SUCCESS("success", "操作成功"),

    FAIL("fail", "操作失败"),

    EMPTY("empty", "暂无数据"),

    EXCEPTION("exception", "系统繁忙，请稍后再试");

    private String code;
    private String message;

    private OperationStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static OperationStatus findStatus(String code) {
        for (OperationStatus status : values()) {
            if (status.code().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("ResultInfo Status not legal:" + code);
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

}
