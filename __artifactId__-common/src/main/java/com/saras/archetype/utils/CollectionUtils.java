package com.saras.archetype.utils;

import java.util.Collection;
import java.util.Stack;

/**
 * 集合工具类
 * <p>
 * by zhanghao 2017/8/9 10:58
 */
public class CollectionUtils {


    /**
     * 判断Collection是否不为空。
     *
     * @param collection 要判断的Collection。
     * @return 如果Collection不为 null 且 {@link Collection#isEmpty()} 返回false时，则返回
     * true。
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return (collection != null && !collection.isEmpty());
    }

    /**
     * 判断Stack是否不为空。
     *
     * @param stack 要判断的Stack。
     * @return 如果Stack不为 null 且 {@link Stack#isEmpty()} 返回false时，则返回 true。
     */
    public static boolean isNotEmpty(Stack<?> stack) {
        return (stack != null && !stack.isEmpty());
    }

}
