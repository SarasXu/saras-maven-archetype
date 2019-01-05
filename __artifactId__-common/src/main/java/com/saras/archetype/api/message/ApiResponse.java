package com.saras.archetype.api.message;


/**
 * description:
 * saras_xu@163.com 2017-11-29 14:30 创建
 */
public class ApiResponse extends ApiBaseMessage {

    private static final long serialVersionUID = 8428789061497772910L;
    /**
     * 业务状态
     */
    private String status;
    /**
     * 描述
     */
    private String message;
    /**
     * 错误编码
     */
    private String code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
