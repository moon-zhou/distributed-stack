# redis om

## 环境
- redis/redis-stack： 包含redis stack和redisInsight，该最适用于本地开发者，可以使用内置的RedisInsigt对数据可视化。
- redis/redis-stack-server： 只提供redis stack服务，最适用于生产部署。

[示例访问地址](http://localhost:8080/swagger-ui/index.html)

## 注意点
注意redis om与之对应的springboot版本，以及其他组件比如swagger的版本，尤其是springboot3之后。

## 一些错误
```
redis.clients.jedis.exceptions.JedisDataException: ERR unknown command 'FT.DROPINDEX', with args beginning with: 'org.moonzhou.redisom.domain.ProductIdx' 'DD'

redis.clients.jedis.exceptions.JedisDataException: ERR unknown command 'JSON.SET'
```


## 参考
1. [Redis OM Spring](https://redis.io/docs/clients/om-clients/stack-spring/)
2. [官方源码及示例](https://github.com/redis/redis-om-spring)
