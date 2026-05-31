package org.moonzhou.springbootserializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Serializer 示例应用主类
 * 
 * 本应用演示了如何自定义序列化和反序列化方法：
 * 1. 入参中特殊注解标注的字段值通过固定算法转换
 * 2. 出参中特殊注解标注的字段值除了原样输出外，还按注解里的value作为字段值再次输出
 * 
 * @author moonzhou
 */
@SpringBootApplication
public class SpringBootSerializerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSerializerApplication.class, args);
    }

}