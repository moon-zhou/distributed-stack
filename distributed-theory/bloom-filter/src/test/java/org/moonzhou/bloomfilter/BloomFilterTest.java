package org.moonzhou.bloomfilter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class BloomFilterTest {

    @Test
    public void testMyBloomFilter() {
        String str = "好好学技术";
        MyBloomFilter myBloomFilter = new MyBloomFilter();
        log.info("str是否存在：{}", myBloomFilter.contains(str));
        myBloomFilter.add(str);
        log.info("str是否存在：{}", myBloomFilter.contains(str));
    }
}