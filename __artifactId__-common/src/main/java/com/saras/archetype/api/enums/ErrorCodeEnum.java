
package com.saras.archetype.api.enums;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:26 创建
 */
public enum ErrorCodeEnum {
    //Finance common error code
    SUCCESS("FN01_00_0000", "成功"),
    PROCESSING("FN01_00_0001", "处理中"),
    UN_KNOWN_ERROR("FN01_00_0002", "未知异常"),
    DB_ERROR("FN01_00_0003", "数据库错误"),
    ILLEGAL_PARAM("FN01_00_0004", "业务参数校验失败"),
    DUPLICATED_ORDER("FN01_00_0005", "重复的订单"),
    ORDER_NOT_EXIST("FN01_00_0006", "订单不存在"),
    RPC_TIME_OUT("FN01_00_0007", "调用下游渠道超时"),
    CHANNEL_FAIL_WITHOUT_ERROR_MESSAGE("FN01_00_0008", "交易失败,渠道未返回错误编码"),
    RPC_QUERY_FAIL("FN01_00_0009", "远程查询渠道结果失败"),
    CHANNEL_FAIL("FN01_00_0010", "渠道同步返回失败"),
    CHANNEL_ILLEGAL_ARGUMENT_ERROR("FN01_00_0011", "渠道消息参数错误"),
    ORDER_STATUS_ERROR("FN01_00_0012", "订单状态错误"),
    DESCRIPTIVE_ERROR("FN01_00_0013", "描述："),


    //内部调api服务返回的错误码
    REQUEST_DATA_NOT_EXIST("FA01_01_0001", "请求对应的原始数据不存在"),
    NOTIFY_URL_IS_BLANK("FA03_01_0002", "商户异步通知地址为空");

    /**
     * 枚举值
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return ErrorCodeEnum
     */
    public static ErrorCodeEnum getByCode(String code) {
        for (ErrorCodeEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举
     *
     * @return List<ErrorCodeEnum>
     */
    public static java.util.List<ErrorCodeEnum> getAllEnum() {
        java.util.List<ErrorCodeEnum> list = new java.util.ArrayList<ErrorCodeEnum>(
                values().length);
        for (ErrorCodeEnum _enum : values()) {
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
        ErrorCodeEnum _enum = getByCode(code);
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
