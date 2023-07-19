package org.moonzhou.bloomfilter;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author moon zhou
 * @version 1.0
 * @description:
 * @date 2023/7/18 22:09
 */
@Slf4j
public class HutoolBloomFilterTest {

    @Test
    public void testHutoolBloomFilter() {
        BitMapBloomFilter bloomFilter = BloomFilterUtil.createBitMap(1000);
        bloomFilter.add("好好学技术");
        log.info("{}", bloomFilter.contains("不好好学技术"));
        log.info("{}", bloomFilter.contains("好好学技术"));
    }
}
