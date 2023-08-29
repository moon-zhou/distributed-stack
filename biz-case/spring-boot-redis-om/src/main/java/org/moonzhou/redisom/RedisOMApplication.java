package org.moonzhou.redisom;

import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 官方源码及示例
 * https://github.com/redis/redis-om-spring
 *
 * 官方文档：
 * https://redis.io/docs/clients/om-clients/stack-spring/
 */
@SpringBootApplication
@EnableRedisDocumentRepositories(basePackages = "org.moonzhou.redisom.*")
public class RedisOMApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisOMApplication.class, args);
    }

}