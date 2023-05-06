package org.moonzhou.distributedlock.util;

import org.junit.Test;
import org.moonzhou.distributedlock.redis.RedisPoolUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class RedisPoolUtilTest {

    public static final String OK = "OK";

    String firstKey = "moon";
    String firstVal = "handSomeBoy";

    String secondKey = "hou";
    String secondValue = "cool";

    @Test
    public void easyTest() {
        Jedis jedis = new Jedis();

        // 本地Redis服务状态，返回PONG--->正常
        String pingResult = jedis.ping();
        assertEquals("PONG", pingResult);


        String moonSetResult = jedis.set(firstKey, firstVal);
        assertEquals(OK, moonSetResult);

        String moonResult = jedis.get(firstKey);
        assertEquals(firstVal, moonResult);

        jedis.close();
    }

    @Test
    public void testSetnx() {
        Long result = RedisPoolUtil.setnx(firstKey, firstVal);

        System.out.println(result);
        String moonVal = RedisPoolUtil.get(firstKey);

        assertEquals(firstVal, moonVal);
    }

    @Test
    public void testSet() {
        SetParams setParams = new SetParams();
        setParams.nx();
        setParams.px(20000);
        String result = RedisPoolUtil.set(secondKey, secondValue, setParams);

        System.out.println(result);
    }

    @Test
    public void testDoLua() {
        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] " +
                "then return redis.call('del', KEYS[1]) else return -1 end";

        Object result = RedisPoolUtil.doLua(luaScript, Collections.singletonList(secondKey),  Collections.singletonList(secondValue));

        System.out.println(result);
    }
}