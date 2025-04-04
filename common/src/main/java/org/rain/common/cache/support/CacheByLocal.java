package org.rain.common.cache.support;

import org.rain.common.cache.CacheObj;
import org.rain.common.cache.ICache;
import org.rain.common.enums.DateUnit;
import org.rain.common.exception.BaseException;
import org.rain.common.util.concurrent.Threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * created by yangtong on 2025/4/4 下午9:00
 * <br/>
 * 基于JVM堆内存的缓存组件
 */
public class CacheByLocal implements ICache {
    private final Map<String, CacheObj> cache = new ConcurrentHashMap<>();

    {
        Threads.execute(() -> {
            while (true) {
                try {
                    for (Map.Entry<String, CacheObj> entry : cache.entrySet()) {
                        CacheObj obj = entry.getValue();
                        if (obj.isExpired()) {
                            cache.remove(entry.getKey());
                        }
                    }

                    Threads.sleep(TimeUnit.SECONDS, 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public <T> void put(String key, T value) {
        put(key, value, DateUnit.MS, -1);
    }

    @Override
    public <T> void put(String key, T value, DateUnit unit, long timeout) {
        if (unit.equals(DateUnit.MONTH) || unit.equals(DateUnit.YEAR)) {
            throw new BaseException("月份或者年份的具体时间长度不确定，无法转换成毫秒数");
        }
        //将超时时间转换成毫秒数
        long ms = unit.getMillis() * timeout;
        CacheObj cacheObj = new CacheObj(value, ms);
        cache.put(key, cacheObj);
    }

    @Override
    public Object get(String key) {
        return get(key, Object.class);
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        CacheObj value = cache.get(key);
        if (value == null || value.isExpired()) {
            // 清理过期数据
            cache.remove(key);
            return null;
        }

        return type.cast(value.value);
    }

    @Override
    public <T> T get(String key, Class<T> type, Supplier<T> supplier, DateUnit unit, long timeout) {
        T value = get(key, type);
        if (value == null) {
            value = supplier.get();
            put(key, value, unit, timeout);
        }
        return value;
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public boolean contains(String key) {
        return cache.containsKey(key);
    }

    @Override
    public List<CacheObj> list(long pageNo, long pageSize) {
        long offset = (pageNo - 1) * pageSize;

        List<CacheObj> cacheObjList = cache.entrySet().stream().map(i -> {
            CacheObj value = i.getValue();
            value.key = i.getKey();
            return value;
        }).sorted((e1, e2) -> e2.cacheAt.compareTo(e1.cacheAt)).toList();


        List<CacheObj> result = new ArrayList<>();

        for (int i = 0; i < pageSize; i++) {
            long current = offset + i;
            if (current < cacheObjList.size()) {
                CacheObj cacheObj = cacheObjList.get((int) current);
                result.add(cacheObj);
            } else {
                break;
            }
        }
        return result;
    }

    @Override
    public void clear() {
        cache.clear();
    }

}
