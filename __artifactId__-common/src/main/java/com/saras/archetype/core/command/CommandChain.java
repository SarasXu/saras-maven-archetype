package com.saras.archetype.core.command;

/**
 * description:
 * saras_xu@163.com 2017-03-28 09:12 创建
 */
public interface CommandChain<R> {
    /**
     * 接收泛型处理对象，使用规则链路进行处理.
     *
     * @param receiveObject 接收参数，并不对所有的具体实现有效.
     * @return 返回任意的目标对象
     */
    void proceed(R receiveObject);
}
