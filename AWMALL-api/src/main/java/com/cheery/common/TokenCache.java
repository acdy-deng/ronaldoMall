package com.cheery.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @desc: token缓存
 * @className: TokenCache
 * @author: RONALDO
 * @date: 2019-02-24 21:49
 */
public class TokenCache {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    /**
     * desc: 加载缓存 采用LRU算法优化缓存
     * initialCapacity(1000) 缓存的初始化容量
     * maximumSize(10000) 缓存的储存容量
     * expireAfterAccess(12, TimeUnit.HOURS) 缓存的有效期
     *
     * @auther RONALDO
     * @date: 2019-02-24 21:52
     */
    private static LoadingCache<String, String> loadCache = CacheBuilder.newBuilder()
            .initialCapacity(1000)
            .maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                // 默认的数据实现,当调用get取值的时候,如果没有key对应的值,就会调用此方法
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setKey(String key, String value) {
        loadCache.put(key, value);
    }

    public static String getKey(String key) {
        String value = null;
        String v = "null";
        try {
            value = loadCache.get(key);
            if (v.equals(value)) {
                return null;
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("druid configuration initialization filter", e);
        }
        return null;
    }

}
