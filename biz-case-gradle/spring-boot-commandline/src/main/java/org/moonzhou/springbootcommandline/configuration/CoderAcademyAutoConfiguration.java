package org.moonzhou.springbootcommandline.configuration;

import org.moonzhou.springbootcommandline.property.CommandLineProperties;
import org.moonzhou.springbootcommandline.service.CommandLineService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

// 使用@Configuration注解表示这是一个配置类
@Configuration
// 使用@EnableConfigurationProperties注解启用我们创建的配置属性类
@EnableConfigurationProperties(CommandLineProperties.class)
public class CoderAcademyAutoConfiguration {

    // 创建一个Bean方法，返回我们的服务类实例
    // 使用@ConditionalOnMissingBean注解，表示只有在Spring容器中没有其他同类型的Bean时，才会创建这个Bean
    @Bean
    @ConditionalOnMissingBean
    public CommandLineService coderAcademyService(CommandLineProperties properties) {
        return new CommandLineService(properties.getName(), properties.getAge());
    }
}