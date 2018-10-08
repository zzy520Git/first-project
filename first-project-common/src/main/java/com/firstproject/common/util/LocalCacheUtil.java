package com.firstproject.common.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/10/8 15:42
 */
public class LocalCacheUtil {
    private static final int cacheSize = 10 ;
    private static final Map<String, Object> CACHE ;
    static {
        /**
         * 缺点是需要自己实现过期逻辑和过期时间判断
         */
        CACHE = Collections.synchronizedMap(new LinkedHashMap<String, Object>(cacheSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return this.size() > LocalCacheUtil.cacheSize ;
            }
        }) ;
    }
}
