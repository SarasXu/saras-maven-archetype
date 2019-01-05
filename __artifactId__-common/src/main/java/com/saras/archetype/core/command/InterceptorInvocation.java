package com.saras.archetype.core.command;

import org.springframework.core.Ordered;

/**
 * description:
 * saras_xu@163.com 2017-03-28 09:28 创建
 */
public interface InterceptorInvocation<R> extends Ordered {
    /**
     * 是否匹配链路处理规则
     *
     * @param receiveObject
     * @return
     */
    boolean matcher(R receiveObject);

//    /**
//     * 前置处理
//     */
//    void postbefore();
//
//    /**
//     * 后置处理
//     */
//    void postalfter();

    /**
     * 设置执行顺序，默认按照加入顺序执行，数字越大，执行越靠后.
     *
     * @param order
     */
    void setOrder(int order);
}
