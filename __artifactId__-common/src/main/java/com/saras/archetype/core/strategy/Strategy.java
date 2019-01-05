package com.saras.archetype.core.strategy;

/**
 * description:策略接口
 * saras_xu@163.com 2017-03-28 13:35 创建
 */
public interface Strategy<O, R> {
    /**
     * 策略方法
     */
    R executionStrategy(O o);

}
