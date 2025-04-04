package org.rain.common.enums;

/**
 * created by yangtong on 2025/4/4 下午8:55
 * <br/>
 * 缓存组件类型
 */
public enum CacheType {
    //基于本地内存的缓存
    LOCAL,
    //基于CAFFEINE组件的缓存，一种基于本地内存的缓存库
    CAFFEINE,
    //基于Redis组件的缓存
    REDIS,
    //基于数据库的缓存
    DB
}
