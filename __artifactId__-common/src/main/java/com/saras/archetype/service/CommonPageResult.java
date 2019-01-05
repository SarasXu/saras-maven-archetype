package com.saras.archetype.service;

import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.saras.archetype.utils.Paginator;

import java.util.List;

/**
 * 分页结果
 * <p>
 * by zhanghao 2017/8/16 13:46
 */
public class CommonPageResult<T> extends ResultInfo {

    private static final long serialVersionUID = -8332042867917476975L;
    private List<T> infoList;
    private Page page;
    private Paginator paginator;

    public CommonPageResult() {
        this(Lists.newArrayList());
    }

    public CommonPageResult(List<T> infoList) {
        this(infoList, new Paginator());
    }

    public CommonPageResult(List<T> infoList, Paginator paginator) {
        this.setInfoList(infoList);
        this.setPaginator(paginator);
    }

    public CommonPageResult(List<T> infoList, Page page) {
        super();
        this.setPage(page);
        this.setInfoList(infoList);
    }

    public List<T> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<T> infoList) {
        this.infoList = infoList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }
}
