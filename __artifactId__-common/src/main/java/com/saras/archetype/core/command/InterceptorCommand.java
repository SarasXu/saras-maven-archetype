package com.saras.archetype.core.command;


/**
 * description:
 * saras_xu@163.com 2017-03-28 09:31 创建
 */
public interface InterceptorCommand<R> extends InterceptorInvocation<R> {
    /**
     * 手动流转的链路，各个节点执行的业务规则
     *
     * @param entity
     * @param chain
     */
    void execute(R entity, CommandChain<R> chain);
}
