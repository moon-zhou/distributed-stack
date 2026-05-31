package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    private static final Logger log = LoggerFactory.getLogger(AsyncService.class);

    @Async("taskExecutor")
    public void asyncTask(String taskName) {
        String traceId = getTraceIdFromThreadLocal();
        log.info("[异步任务] 开始执行 - 任务名：{}, Thread: {}, TraceId: {}",
                taskName,
                Thread.currentThread().getName(),
                traceId);

        try {
            Thread.sleep(100);
            log.info("[异步任务] 执行中 - 任务名：{}, Thread: {}, TraceId: {}", taskName, Thread.currentThread().getName(), getTraceIdFromThreadLocal());
            Thread.sleep(100);

            if ("error".equals(taskName)) {
                throw new RuntimeException("[异步任务] 模拟运行时异常 - TraceID: " + traceId);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("[异步任务] 被中断 - 任务名：{}", taskName, e);
        } catch (RuntimeException e) {
            log.error("[异步任务] 执行失败 - 任务名：{}, TraceId: {}", taskName, traceId, e);
            throw e;
        }

        log.info("[异步任务] 执行完成 - 任务名：{}, Thread: {}, TraceId: {}", taskName, Thread.currentThread().getName(), getTraceIdFromThreadLocal());
    }

    @Async("taskExecutor")
    public void parallelTask(int taskId) {
        String traceId = getTraceIdFromThreadLocal();
        log.info("[并行任务] 执行 - ID: {}, Thread: {}, TraceId: {}",
                taskId,
                Thread.currentThread().getName(),
                traceId);
    }

    private String getTraceIdFromThreadLocal() {
        try {
            return com.example.demo.util.TraceIdUtil.getTraceId();
        } catch (Exception e) {
            return "unknown";
        }
    }
}
