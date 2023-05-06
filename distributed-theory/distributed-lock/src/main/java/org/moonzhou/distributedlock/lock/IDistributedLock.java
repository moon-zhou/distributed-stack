package org.moonzhou.distributedlock.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Description 分布式锁定义接口类，参考jdk的Lock接口
 * @Author moon-zhou <ayimin1989@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/7/28
 */
public interface IDistributedLock {

    /**
     * 加锁:获取锁直到成功
     * 成功lockId，存储在具体方案的抽象的实力对象里，用来解锁
     */
    void lock();

    /**
     * 尝试加锁，如果在设定时间内没获取成功，返回
     *     成功：具体方案抽象的实力对象里记录lockId
     *     失败：记录null
     */
    void tryLock();

    /**
     * 尝试获取锁 可以指定尝试时间
     *    成功：具体方案抽象的实力对象里记录lockId
     *    失败：记录null
     * @param tryLockTime 尝试获取时间
     * @param timeUnit    时间单位
     *
     */
    void tryLock(long tryLockTime, TimeUnit timeUnit);

    /**
     * 释放锁
     */
    void unlock();
}
