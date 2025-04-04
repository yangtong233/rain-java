package org.rain.common.cache;

/**
 * created by yangtong on 2025/4/4 下午9:34
 * <br/>
 * 缓存对象
 */
public class CacheObj {
    //键
    public String key;
    //值
    public Object value;
    //缓存放入时间
    public Long cacheAt;
    //过期时间
    public Long expireAt;

    //设置缓存对象，value：缓存值 timeout：过期时间(ms)
    public CacheObj(Object value, long timeout) {
        long now = System.currentTimeMillis();
        this.value = value;
        this.cacheAt = now;
        this.expireAt = timeout > 0 ? now + timeout : -1;
    }

    //判断是否过期
    public boolean isExpired() {
        return expireAt > 0 && System.currentTimeMillis() > expireAt;
    }
}
