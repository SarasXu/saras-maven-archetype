package com.saras.archetype.core.command;


import java.util.Arrays;
import java.util.List;

/**
 * description:
 * saras_xu@163.com 2017-03-28 10:13 创建
 */
public abstract class CommandChainBase<R> {
    /**
     * 规则命令集合
     */
    private List<InterceptorInvocation<R>> commands;

    /**
     * 构造过程完整排序,并完成关联
     *
     * @param commands
     */
    public CommandChainBase(InterceptorInvocation<R>[] commands) {
        Arrays.sort(commands);
        this.commands = Arrays.asList(commands);
    }

    public List<InterceptorInvocation<R>> getCommands() {
        return this.commands;
    }

    public void setCommands(List<InterceptorInvocation<R>> commands) {
        this.commands = commands;
    }
}
