package org.moonzhou.serializer.config;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author moon zhou
 * @version 1.0
 * @description: env init after spring context already
 */
@Slf4j
@AllArgsConstructor
@Component
public class SerializerApplicationRunner implements ApplicationRunner {

    /**
     * 添加序列化配置：
     * org.moonzhou.serializer.config.WebAutoConfiguration#objectMapper()
     *
     * 如果最初的objectMapper本身没有配置String的序列化，可以在程序启动之后，动态添加序列化的方式
     */
    private final ObjectMapper objectMapper;

    private final JsonSerializer<String> dangerousCharactersSerializer;
    private final JsonDeserializer<String> dangerousCharactersDeSerializer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // SimpleModule simpleModule = new SimpleModule();
        // simpleModule.addSerializer(String.class, dangerousCharactersSerializer);
        // simpleModule.addDeserializer(String.class, dangerousCharactersDeSerializer);
        //
        // objectMapper.registerModule(simpleModule);
    }
}
