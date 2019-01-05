package com.saras.archetype.utils;

import com.github.pagehelper.PageInfo;
import com.saras.archetype.base.ToString;

import java.io.Serializable;

/**
 * 一个分页器
 * Created by zhanghao on 2017/9/15.
 */
public class Paginator implements Serializable, Cloneable {

    /**
     * 每页默认的项数(10)。
     */
    public static final int DEFAULT_ITEMS_PER_PAGE = 10;
    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE_NO = 1;
    /**
     * 表示项数未知(<code>0</code>)。
     */
    public static final int UNKNOWN_ITEMS = 0;
    private static final long serialVersionUID = -4649355779486329868L;
    /**
     * 当前页码。1--   起始值为1
     */
    private int pageNo;
    /**
     * 每页需要的条数
     */
    private int pageSize;
    /**
     * 总数
     */
    private int totalItem;
    /**
     * 实际结果中每页条数
     */
    private int perPageItem;
    /**
     * 总页数
     */
    private int totalPage;

    public Paginator() {
        this(DEFAULT_PAGE_NO, DEFAULT_ITEMS_PER_PAGE);
    }

    public Paginator(int pageNo, int pageSize) {
        super();
        this.pageNo = pageNo > 0 ? pageNo : DEFAULT_PAGE_NO;
        this.pageSize = pageSize > 0 ? pageSize : DEFAULT_ITEMS_PER_PAGE;
        this.totalItem = UNKNOWN_ITEMS;
        this.perPageItem = DEFAULT_ITEMS_PER_PAGE;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalPage = (int) Math.ceil((double) this.totalItem / this.perPageItem);
        this.totalItem = totalItem;
    }

    public int getPerPageItem() {
        return perPageItem;
    }

    public void setPerPageItem(int perPageItem) {
        this.totalPage = (int) Math.ceil((double) this.totalItem / this.perPageItem);
        this.perPageItem = perPageItem;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void tranfer(PageInfo pageInfo) {
        this.setPageNo(pageInfo.getPageNum());
        this.setPerPageItem(pageInfo.getPrePage());
        this.setTotalItem((int) pageInfo.getTotal());
        this.setPageSize(pageInfo.getPageSize());

    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
