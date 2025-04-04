package org.rain.common.util;

import org.rain.common.exception.BaseException;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * created by yangtong on 2025/4/4 下午4:36
 * <br/>集合工具类
 */
public class Colls {

    /**
     * 经过条件筛选后，获取容器内唯一一个元素
     */
    public static <T> T one(Collection<T> collection, Predicate<T> predicate) {
        List<T> candidateItem = collection.stream().filter(predicate).toList();
        if (candidateItem.size() > 1) {
            throw new BaseException("期待容器集合" + collection + "只有一个元素，但是实际发现了" + candidateItem.size() + "个");
        } else if (candidateItem.size() == 1) {
            return candidateItem.get(0);
        }
        return null;
    }

    /**
     * 判断容器是否为空
     */
    public static <T> Boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断容器是否为空
     */
    public static <T> Boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }


}
