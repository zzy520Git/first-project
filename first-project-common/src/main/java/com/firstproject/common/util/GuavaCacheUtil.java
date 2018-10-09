package com.firstproject.common.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description：
 * Guava缓存实例模板
 * @author zhouzhongyi1
 * DATE： 2018/10/8 15:42
 */
public class GuavaCacheUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaCacheUtil.class);

    private static final Cache<String, Object> CACHE;

    static {
        CACHE = CacheBuilder
                .newBuilder()
                /**
                 * 设置初始容量
                 */
                .initialCapacity(5)
                /**
                 * 设置最大容量，超过使用LRU淘汰缓存
                 */
                .maximumSize(10)
                /**
                 * 设置过期时间，根据业务需要也可选用expireAfterWrite
                 */
                .expireAfterAccess(3*60, TimeUnit.SECONDS)
                /**
                 * 设置并发级别，并发级别是指可以同时写缓存的线程数
                 */
                .concurrencyLevel(8)
                /**
                 * 设置要统计缓存的命中率
                 */
                .recordStats()
                /**
                 * 设置缓存被移除的监听事件
                 */
                .removalListener(rn -> {
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("被移除缓存{}:{}", rn.getKey(), rn.getValue());
                    }
                }).build();
    }

    public static Object get(String key) {
        return StringUtils.isNotEmpty(key) ? CACHE.getIfPresent(key) : null;
    }

    public static void put(String key, Object value) {
        if (StringUtils.isNotEmpty(key) && value != null) {
            CACHE.put(key, value);
        }
    }

    public static void remove(String key) {
        if (StringUtils.isNotEmpty(key)) {
            CACHE.invalidate(key);
        }
    }

    public static void remove(List<String> keys) {
        if (keys != null && keys.size() > 0) {
            CACHE.invalidateAll(keys);
            //另外invalidateAll()可清理所有缓存
        }
    }

    /** Returns the approximate number of entries in this cache. */
    public static long size() {
        return CACHE.size() ;
    }

    public static void main(String[] args) {
        GuavaCacheUtil.put("1", "小王");
        GuavaCacheUtil.put("2", "小张");
        GuavaCacheUtil.put("3", "小名");
        GuavaCacheUtil.put("4", "小明同学");
        GuavaCacheUtil.get("4") ;
        GuavaCacheUtil.get("6") ;
        System.out.println(CACHE.stats().toString());
        CACHE.asMap().forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }
}
