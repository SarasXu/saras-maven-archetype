package com.saras.archetype.service;


import java.util.List;

/**
 * lis返回结果对象
 * <p>
 * by zhanghao 2017/8/23 10:21
 */
public class ListResult<T> extends ResultInfo {

    private List<T> infoList;

    public List<T> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<T> infoList) {
        this.infoList = infoList;
    }
}
