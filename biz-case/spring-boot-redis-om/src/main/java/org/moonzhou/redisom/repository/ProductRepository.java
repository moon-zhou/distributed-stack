package org.moonzhou.redisom.repository;

import com.redis.om.spring.repository.RedisDocumentRepository;
import org.moonzhou.redisom.domain.Product;

import java.util.List;

/**
 * 商品管理Repository
 */
public interface ProductRepository extends RedisDocumentRepository<Product, Long> {
    /**
     * 根据品牌名称查询
     */
    List<Product> findByBrandName(String brandName);

    /**
     * 根据名称或副标题搜索
     */
    List<Product> findByNameOrSubTitle(String name, String subTitle);
}