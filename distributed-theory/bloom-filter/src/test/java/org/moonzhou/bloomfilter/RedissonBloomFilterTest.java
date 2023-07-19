package org.moonzhou.bloomfilter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author moon zhou
 * @version 1.0
 * @description:
 * @date 2023/7/18 22:09
 */
@Slf4j
public class RedissonBloomFilterTest {

    @Test
    public void testGuavaBloomFilter() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        //构造Redisson
        RedissonClient redisson = Redisson.create(config);

        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("name");
        //初始化布隆过滤器：预计元素为100000000L,误差率为1%
        bloomFilter.tryInit(100000000L,0.01);
        bloomFilter.add("好好学技术");

        log.info("{}", bloomFilter.contains("不好好学技术"));
        log.info("{}", bloomFilter.contains("好好学技术"));
    }
}
