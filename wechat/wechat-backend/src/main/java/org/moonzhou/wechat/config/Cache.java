package org.moonzhou.wechat.config;

import cn.hutool.cache.impl.TimedCache;
import lombok.Data;

/**
 * @author moon zhou
 * @version 1.0
 * @description: cache, actually can be replaced by redis. This demo also can use Hutool cache.
 * @date 2023/3/22 22:03
 */
@Data
public class Cache {

    private TimedCache<String, String> cache;

    private static Cache instance = null;

    private Cache() {

    }

    public static Cache getInstance() {
        if (instance == null) {
            synchronized (Cache.class) {
                if (instance == null) {
                    instance = new Cache();
                }
            }
        }
        return instance;
    }
}
