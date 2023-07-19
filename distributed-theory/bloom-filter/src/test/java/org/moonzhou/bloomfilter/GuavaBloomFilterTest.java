package org.moonzhou.bloomfilter;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author moon zhou
 * @version 1.0
 * @description:
 * @date 2023/7/18 22:09
 */
@Slf4j
public class GuavaBloomFilterTest {

    @Test
    public void testGuavaBloomFilter() {
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 100000, 0.01);
        bloomFilter.put("好好学技术");
        log.info("{}", bloomFilter.mightContain("不好好学技术"));
        log.info("{}", bloomFilter.mightContain("好好学技术"));
    }

    @Test
    public void testGuavaBloomFilterMisjudgment() {
        int size = 1000000;// 预计要插入多少数据
        double fpp = 0.01;// 期望的误判率
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

        //插入数据
        for (int i = 0; i < 1000000; i++) {
            bloomFilter.put(i);
        }

        int count = 0;
        for (int i = 1000000; i < 2000000; i++) {
            if (bloomFilter.mightContain(i)) {
                count++;
                log.info("{} 误判了", i);
            }
        }
        log.info("总共的误判数: {}, 误判率: {}", count, BigDecimal.valueOf(count / 1000000D).setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
