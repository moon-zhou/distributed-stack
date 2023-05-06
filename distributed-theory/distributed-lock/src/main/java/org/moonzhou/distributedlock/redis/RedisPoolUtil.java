package org.moonzhou.distributedlock.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.List;

/**
 * redis常规操作
 *
 * @author moon zhou
 * @date 2019-12-01
 */
public class RedisPoolUtil {

    private RedisPoolUtil() {
    }

    /**
     * get String 值
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedisResource();
            result = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null == jedis) {
                jedis.close();
            }

            return result;
        }
    }

    /**
     * 设置String值，成功返回OK
     * 核心点是设置值，原来没有这个key则添加，原来有值则更新这个key，操作成功了返回OK
     *
     * @param key
     * @param value
     * @return
     */
    public static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedisResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null == jedis) {
                jedis.close();
            }

            return result;
        }
    }

    /**
     * 设置String值，同时可以设置参数
     * String result = jedis.set(realKey, value, "NX", "PX", lockExpireTimeOut);
     * <p>
     * 不存在则设置，设置成功返回OK，如果存在，则不设置，返回null
     *
     * @param key
     * @param value
     * @param setParams
     * @return
     */
    public static String set(String key, String value, SetParams setParams) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedisResource();
            result = jedis.set(key, value, setParams);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null == jedis) {
                jedis.close();
            }

            return result;
        }
    }

    /**
     * 单纯的setnx，不存在设置，同时返回1，存在则不设置返回0
     *
     * @param key
     * @param value
     * @return
     */
    public static Long setnx(String key, String value) {
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getJedisResource();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null == jedis) {
                jedis.close();
            }

            return result;
        }
    }

    public static String getSet(String key, String value) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = RedisPool.getJedisResource();
            result = jedis.getSet(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null == jedis) {
                jedis.close();
            }

            return result;
        }
    }

    public static Long expire(String key, int seconds) {
        if (seconds <= 0) {
            return 0L;
        }

        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getJedisResource();
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null == jedis) {
                jedis.close();
            }

            return result;
        }
    }

    public static Long del(String key) {
        Jedis jedis = null;
        Long result = null;

        try {
            jedis = RedisPool.getJedisResource();
            result = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null == jedis) {
                jedis.close();
            }

            return result;
        }
    }

    /**
     * 调用执行lua脚本
     *
     * @param luaScript
     */
    public static Object doLua(String luaScript, List<String> keys, List<String> args) {
        Jedis jedis = null;
        Object result = null;

        try {
            jedis = RedisPool.getJedisResource();
            result = jedis.eval(luaScript, keys, args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null == jedis) {
                jedis.close();
            }

            return result;
        }
    }
}
