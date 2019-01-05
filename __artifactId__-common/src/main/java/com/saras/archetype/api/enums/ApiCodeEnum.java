package com.saras.archetype.api.enums;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:27 创建
 */
public enum ApiCodeEnum {
    UN_AUTHENTICATED("UN_AUTHENTICATED", "认证签名错误"),

    UN_AUTHORIZED("UN_AUTHORIZED", "未授权的服务或渠道Id错误"),

    INTERNAL_ERROR("INTERNAL_ERROR", "内部错误"),

    REQUEST_ID_NOT_UNIQUE("REQUEST_ID_NOT_UNIQUE", "请求号不唯一"),

    ILLEGAL_PARAMETER("ILLEGAL_PARAMETER", "参数错误"),

    ORDER_NOT_EXIST("ORDER_NOT_EXIST", "订单不存在"),

    ORDER_STATUS_ERROR("ORDER_NOT_EXIST", "订单状态错误"),

    SERVICE_NOT_EXIST("SERVICE_NOT_EXIST", "请求的服务不存在,请检查服务名和版本号"),

    DUPLICATED_ORDER("DUPLICATED_ORDER", "重复的订单"),

    BUSINESS_PARAM_CHECK_ERROR("BUSINESS_PARAM_CHECK_ERROR", "业务参数检查错误"),

    PARAM_DESCRIPTIVE_ERROR("PARAM_DESCRIPTIVE_ERROR", "描述："),

    QUERY_FAIL("QUERY_FAIL", "查询失败"),

    SUCCESS("SUCCESS", "成功"),
    ;
    /**
     * 枚举值
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String message;

    ApiCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return ApiCodeEnum
     */
    public static ApiCodeEnum getByCode(String code) {
        for (ApiCodeEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<ApiCodeEnum>
     */
    public static java.util.List<ApiCodeEnum> getAllEnum() {
        java.util.List<ApiCodeEnum> list = new java.util.ArrayList<ApiCodeEnum>(
                values().length);
        for (ApiCodeEnum _enum : values()) {
            list.add(_enum);
        }
        return list;
    }

    /**
     * 通过code获取msg
     *
     * @param code 枚举值
     * @return
     */
    public static String getMsgByCode(String code) {
        if (code == null) {
            return null;
        }
        ApiCodeEnum _enum = getByCode(code);
        if (_enum == null) {
            return null;
        }
        return _enum.getMessage();
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
