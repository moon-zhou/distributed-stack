package org.moonzhou.distributedstack.springboot4performancetest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author moon zhou
 */
@Configuration
public class SseConfig {
    
    /**
     * 配置异步任务执行器
     */
    @Bean
    public ThreadPoolTaskExecutor sseTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 10);
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 50);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("moon-zhou-sse-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}