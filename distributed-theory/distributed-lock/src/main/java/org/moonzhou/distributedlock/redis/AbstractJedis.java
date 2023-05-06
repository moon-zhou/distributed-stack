package org.moonzhou.distributedlock.redis;

/**
 * <p>Jedis调用抽象类, 对Jedis操作再进行一层封装<p/>
 *
 * @author moon-zhou
 */
public abstract class AbstractJedis {

    /**
     * key值，对应公共资源
     */
    private String key;

    /**
     * value值，唯一，对应到获取到锁的线程
     * 解锁时使用，保证加解锁的一一对应
     */
    private String value;

    protected String getKey() {
        return key;
    }

    protected void setKey(String key) {
        this.key = key;
    }

    protected String getValue() {
        return value;
    }

    protected void setValue(String value) {
        this.value = value;
    }
}