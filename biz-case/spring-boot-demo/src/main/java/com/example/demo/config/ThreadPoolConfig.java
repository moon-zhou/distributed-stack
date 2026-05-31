package com.example.demo.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.example.demo.util.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-thread-");
        executor.setKeepAliveSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);

        // 添加 TaskDecorator 用于在子线程中初始化 MDC
        executor.setTaskDecorator(task -> {
            return () -> {
                TraceIdUtil.initMDCInChildThread();
                task.run();
            };
        });

        executor.afterPropertiesSet();

        Executor ttlExecutor = TtlExecutors.getTtlExecutor(executor);
        log.info("创建 TTL 线程池：{}", ttlExecutor);

        return ttlExecutor;
    }
}
