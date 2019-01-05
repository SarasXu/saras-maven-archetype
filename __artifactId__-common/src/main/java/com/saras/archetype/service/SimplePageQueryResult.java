package com.saras.archetype.service;

import com.github.pagehelper.PageInfo;

/**
 * description:
 * saras_xu@163.com 2017-12-19 08:40 创建
 */
public class SimplePageQueryResult<Entity> extends BasePageResult {
    private static final long serialVersionUID = -2733132636310391410L;
    /**
     * 返回分页结果
     */
    private PageInfo<Entity> pageInfo;

    public PageInfo<Entity> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<Entity> pageInfo) {
        this.pageInfo = pageInfo;
    }
}
