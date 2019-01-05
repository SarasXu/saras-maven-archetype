package com.saras.archetype.api.message;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:27 创建
 */
public class ApiNotify extends ApiResponse {
    private static final long serialVersionUID = -4986311328591958692L;
    /**
     * 通知时间
     */
    private String notifyTime;

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }
}
