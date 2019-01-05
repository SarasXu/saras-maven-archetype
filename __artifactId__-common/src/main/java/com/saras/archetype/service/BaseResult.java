package com.saras.archetype.service;


import com.saras.archetype.base.Base;
import com.saras.archetype.enums.Status;

/**
 * message:
 * saras_xu@163.com 2017-03-01 14:45 创建
 */
public abstract class BaseResult extends Base {
    private static final long serialVersionUID = 7964440651025911165L;
    /**
     * 结果状态
     */
    protected Status status;
    /**
     * 描述
     */
    protected String message;
    /**
     * 错误码
     */
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.status == Status.SUCCESS;
    }

    public boolean isFail() {
        return this.status == Status.FAIL;
    }

    public boolean isProcessing() {
        return this.status == Status.PROCESSING;
    }

}
