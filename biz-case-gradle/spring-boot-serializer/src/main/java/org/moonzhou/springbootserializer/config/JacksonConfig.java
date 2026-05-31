package org.moonzhou.springbootserializer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.moonzhou.springbootserializer.model.User;
import org.moonzhou.springbootserializer.serializer.CustomDeserializer;
import org.moonzhou.springbootserializer.serializer.CustomSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Jackson配置类
 */
@Configuration
public class JacksonConfig {
    
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        
        SimpleModule module = new SimpleModule();
        // 注册User类的自定义序列化器和反序列化器
        module.addSerializer(User.class, new CustomSerializer());
        module.addDeserializer(User.class, new CustomDeserializer());
        
        mapper.registerModule(module);
        return mapper;
    }
}