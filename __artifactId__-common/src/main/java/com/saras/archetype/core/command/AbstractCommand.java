package com.saras.archetype.core.command;

import org.springframework.core.Ordered;

/**
 * message:
 * saras_xu@163.com 2017-03-28 09:14 创建
 */
public abstract class AbstractCommand<R> implements Comparable<Ordered>, InterceptorCommand<R> {

    /**
     * 默认为最低处理级别
     */
    protected int order = LOWEST_PRECEDENCE;

    @Override
    public int getOrder() {
        if (order < 0) {
            //若赋予小于0的排序，那么则认为始终为最高级别。
            order = HIGHEST_PRECEDENCE;
        }
        return order;
    }

    /**
     * 排序规则应当为正数。
     *
     * @param order 排序参数
     */
    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean matcher(R receiveObject) {
        return Boolean.TRUE;
    }

    @Override
    public int compareTo(Ordered command) {
        return Integer.compare(getOrder(), command.getOrder());
    }

}
