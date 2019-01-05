package com.saras.archetype.api.message;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:27 创建
 */
public class ApiRequest extends ApiBaseMessage {
    private static final long serialVersionUID = -2568700168264180072L;
    /**
     * 异步通知地址
     */
    private String notifyUrl;

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
