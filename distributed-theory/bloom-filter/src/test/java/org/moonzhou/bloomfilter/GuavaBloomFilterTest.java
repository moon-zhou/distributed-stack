package org.moonzhou.bloomfilter;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
}
