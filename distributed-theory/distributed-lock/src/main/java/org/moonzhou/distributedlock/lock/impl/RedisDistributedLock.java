package org.moonzhou.distributedlock.lock.impl;


import org.moonzhou.distributedlock.lock.IDistributedLock;
import org.moonzhou.distributedlock.redis.AbstractJedis;
import org.moonzhou.distributedlock.redis.RedisPoolUtil;
import org.moonzhou.distributedlock.util.IdUtils;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description 分布式锁的实现方式之一：缓存-redis
 * @Author moon-zhou <ayimin1989@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/7/28
 */
public class RedisDistributedLock extends AbstractJedis implements IDistributedLock {

    private static final String LOCK_SUCCESS = "OK";

    /**
     * 尝试加锁直到成功
     */
    private static final int TRY_LOCK_TIME_UNTIL_SUC = -1;

    /**
     * 尝试加锁的默认时间
     */
    private static final int TRY_LOCK_TIME_DEFAULT = 2000;

    /**
     * 加锁时间默认单位：毫秒
     */
    private static final TimeUnit TRY_LOCL_TIMEUNIT_DEFAULT = TimeUnit.MILLISECONDS;

    /**
     * 默认锁过期时间 30s
     */
    private int lockTimeout = 30000;

    /**
     * 重试时间 0.5s, 这里的单位是毫秒
     */
    private int retryTime = 500;

    /**
     * 初始化锁以及需要竞争的资源名称
     *
     * @param lockName 需要获取的资源名称
     */
    public RedisDistributedLock(String lockName) {
        setKey(lockName);
    }

    /**
     * 初始化锁
     * 需要竞争的资源名称
     * 锁持有的过期时间（避免永久持有锁而其他线程无法获取到锁的情况）
     *
     * @param lockName    竞争的资源名称
     * @param lockTimeout 锁持有的过期时间
     */
    public RedisDistributedLock(String lockName, int lockTimeout) {
        setKey(lockName);
        this.lockTimeout = lockTimeout;
    }

    /**
     * 加锁:获取锁直到成功
     *
     * @return 成功lockId，用来解锁
     */
    @Override
    public void lock() {

        this.tryLock(TRY_LOCK_TIME_UNTIL_SUC, null);
    }

    /**
     * 尝试加锁，如果在设定时间内没获取成功，返回
     *
     * @return 成功：<code>return lockId<coe/>
     * 失败：<code>return null<code/>
     */
    @Override
    public void tryLock() {
        this.tryLock(TRY_LOCK_TIME_DEFAULT, TRY_LOCL_TIMEUNIT_DEFAULT);
    }

    @Override
    public void tryLock(long tryLockTime, TimeUnit timeUnit) {
        // 是否一直获取锁，直到成功
        boolean forever = tryLockTime < 0;

        // 开始获取锁的时间
        final long startTime = System.currentTimeMillis();
        if (forever) {
            tryLockTime = 0;
        }

        // 统一转为毫秒
        final Long tryTime = (timeUnit != null) ? timeUnit.toMillis(tryLockTime) : 0;

        String lockSuccess = null;

        /**
         * 尝试次数，线程每次的尝试时间为睡眠时间*次数
         */
        int tryTimes = 0;
        // 如果没有加锁成功，循环尝试获取锁
        while (null == lockSuccess) {
            lockSuccess = doLock();

            // 获取成功 退出
            if (lockSuccess != null) {
                break;
            }

            // 如果不是必须获取到锁，超过了获取锁的最长时间，退出
            if (!forever && (System.currentTimeMillis() - startTime - retryTime * tryTimes) > tryTime) {
                break;
            }

            // 睡眠重试时间，避免太频繁的重试
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(retryTime));
        }

        // 实力对象设置获取到的锁的id，避免方法里直接返回，整个加解锁相对独立与解耦
        setValue(lockSuccess);
    }


    /**
     * @return 锁的唯一value值
     */
    private String doLock() {
        // key对应的value值，表示解锁时用来判断，避免错误解锁
        String lockId = IdUtils.getUUID();

        // String result = jedis.set(lockKey, uniqueId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        SetParams setParams = new SetParams();
        setParams.nx();
        setParams.px(lockTimeout);
        String result = RedisPoolUtil.set(getKey(), lockId, setParams);

        return LOCK_SUCCESS.equals(result) ? lockId : null;
    }

    /**
     * 释放锁，如果要做成返回值，可以根据结果来
     * doLua返回1表示成功，-1表示失败
     */
    @Override
    public void unlock() {
        // 如果get key的值和预期一样 就删除该key
        // 实际上就是保证由加锁的人来解锁
        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] " +
                "then return redis.call('del', KEYS[1]) else return -1 end";

        RedisPoolUtil.doLua(luaScript,
                Collections.singletonList(getKey()), Collections.singletonList(getValue()));
    }
}
