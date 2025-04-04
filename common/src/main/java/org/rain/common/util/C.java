package org.rain.common.util;

import org.rain.common.cache.CacheObj;
import org.rain.common.cache.ICache;
import org.rain.common.enums.DateUnit;

import java.util.List;
import java.util.function.Supplier;

/**
 * created by yangtong on 2025/4/4 下午10:27
 * <br/>
 * 缓存工具类
 */
public class C {

    private static ICache cache;

    /**
     * 添加缓存
     */
    public static <T> void put(String key, T value) {
        cache.put(key, value);
    }

    /**
     * 添加带有过期时间的缓存
     */
    public static  <T> void put(String key, T value, DateUnit unit, long timeout) {
        cache.put(key, value, unit, timeout);
    }

    /**
     * 获取缓存
     */
    public static <T> T get(String key) {
        return cache.get(key);
    }

    /**
     * 获取指定类型的缓存
     */
    public static <T> T get(String key, Class<T> type) {
        return cache.get(key, type);
    }

    /**
     * 获取指定类型的缓存，如果没有，则按照指定的逻辑放一份带有过期时间的缓存
     */
    public static <T> T get(String key, Class<T> type, Supplier<T> supplier, DateUnit unit, long timeout) {
        return cache.get(key, type, supplier, unit, timeout);
    }

    /**
     * 移除缓存
     */
    public static void remove(String key) {
        cache.remove(key);
    }

    /**
     * 判断是否包含指定缓存
     */
    public static boolean contains(String key) {
        return cache.contains(key);
    }

    /**
     * 分页获取缓存
     */
    public static List<CacheObj> list(long pageNo, long pageSize) {
        return cache.list(pageNo, pageSize);
    }

    /**
     * 清理所有缓存
     */
    public static void clear() {
        cache.clear();
    }
}
