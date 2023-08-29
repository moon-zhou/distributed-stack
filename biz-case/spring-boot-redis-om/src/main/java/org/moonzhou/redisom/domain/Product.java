package org.moonzhou.redisom.domain;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import com.redis.om.spring.repository.query.SearchLanguage;
import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * 商品实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor(staticName = "of")
// @NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Document(language = SearchLanguage.CHINESE, timeToLive = 60000)
public class Product {
    @Id
    private Long id;
    @Indexed
    private String productSn;
    @Searchable
    private String name;
    @Searchable
    private String subTitle;
    @Indexed
    private String brandName;
    @Indexed
    private Integer price;
    @Indexed
    private Integer count;
}