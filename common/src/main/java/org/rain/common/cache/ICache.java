package org.rain.common.cache;

import org.rain.common.enums.DateUnit;

import java.util.List;
import java.util.function.Supplier;

/**
 * created by yangtong on 2025/4/4 下午8:54
 * <br/>
 * 缓存规范接口
 */
public interface ICache {
    /**
     * 添加缓存
     */
    <T> void put(String key, T value);

    /**
     * 添加带有过期时间的缓存
     */
    <T> void put(String key, T value, DateUnit unit, long timeout);

    /**
     * 获取缓存
     */
    Object get(String key);

    /**
     * 获取指定类型的缓存
     */
    <T> T get(String key, Class<T> type);

    /**
     * 获取指定类型的缓存，如果没有，则按照指定的逻辑放一份带有过期时间的缓存
     */
    <T> T get(String key, Class<T> type, Supplier<T> supplier, DateUnit unit, long timeout);

    /**
     * 移除缓存
     */
    void remove(String key);

    /**
     * 判断是否包含指定缓存
     */
    boolean contains(String key);

    /**
     * 分页获取缓存
     */
    List<CacheObj> list(long pageNo, long pageSize);

    /**
     * 清理所有缓存
     */
    void clear();
}
