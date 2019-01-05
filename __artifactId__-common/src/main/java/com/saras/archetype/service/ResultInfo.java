package com.saras.archetype.service;


import com.saras.archetype.enums.Status;

/**
 * 返回结果信息
 * <p>
 * by zhanghao 2017/8/9 13:45
 */
public class ResultInfo extends BaseResult {

    private static final long serialVersionUID = 4007781297598863555L;

    public void successResult() {
        this.setCode(Status.SUCCESS.code());
        this.setMessage("处理成功");
        this.setStatus(Status.SUCCESS);
    }

    public void failResult(String message) {
        failResult(message, Status.FAIL.code());
    }

    public void failResult(String messge, String code) {
        this.setCode(code);
        this.setMessage(messge);
        this.setStatus(Status.FAIL);
    }
}
