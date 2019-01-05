package com.saras.archetype.core.exception;


/**
 * description: 业务错误异常
 * saras_xu@163.com 2017-03-01 11:19 创建
 *
 * @author saras
 */
public class BizErrorException extends RuntimeException {

    private static final long serialVersionUID = 2492449662231853945L;

    private String code;

    public BizErrorException() {
        super();
    }

    /**
     * 构建一个<code>BizErrorException.java</code>
     * msg为自定义信息
     * 此方法适用于 直接抛出自己定义的信息
     *
     * @param msg
     */
    public BizErrorException(String msg) {
        super(msg);
    }

    /**
     * 抛出一个自定义的错误码信息的业务错误
     *
     * @param message 信息
     * @param code    错误码
     */
    public BizErrorException(String message, String code) {
        super(message);
        this.code = code;
    }


    public BizErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizErrorException(Throwable cause) {
        super(cause);
    }

    /**
     * 构建一个<code>BizErrorException.java</code>
     * msg为自定义信息
     * 此方法适用于 直接抛出自己定义的信息
     *
     * @param msg
     */
    public static BizErrorException newBizError(String msg) {
        return new BizErrorException(msg);
    }

    /**
     * 抛出一个自定义的错误码信息的业务错误
     *
     * @param msg  信息
     * @param code 错误码
     */
    public static BizErrorException newBizError(String msg, String code) {
        return new BizErrorException(msg, code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
