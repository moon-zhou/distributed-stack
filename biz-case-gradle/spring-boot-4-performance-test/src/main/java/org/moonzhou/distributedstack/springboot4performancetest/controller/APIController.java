package org.moonzhou.distributedstack.springboot4performancetest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author moon zhou
 * @description 本方法暴露http接口，给其他系统调用
 * 1. 普通get方法
 * 2. 普通post方法
 * 3. 流式返回方法
 * @email
 * @date 2026/1/27 10:25
 **/
@Slf4j
@RestController
@RequestMapping("/api")
public class APIController {

    /**
     * 普通GET方法，接收查询参数作为入参，返回JSON
     */
    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> hello(@RequestParam(required = false, defaultValue = "world") String name,
                                     @RequestParam(required = false) String requestId) {
        return buildSuccessResponse(Map.of(
                "greeting", "Hello, " + name + "!",
                "processed", true,
                "server", "APIController",
                "timestamp", System.currentTimeMillis(),
                "requestId", requestId
        ));
    }

    /**
     * 普通POST方法，接收JSON入参并添加标志后返回
     */
    @PostMapping(value = "/data", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> postData(@RequestBody Map<String, Object> request,
                                        @RequestHeader(value = "X-Request-ID", required = false) String headerRequestId) {
        Map<String, Object> data = new HashMap<>(request);
        data.putAll(Map.of(
                "processed", true,
                "server", "APIController",
                "timestamp", System.currentTimeMillis()
        ));
        if (headerRequestId != null) {
            data.put("requestId", headerRequestId);
        }

        return buildSuccessResponse(data);
    }

    /**
     * 流式返回方法（Server-Sent Events），接收请求参数并返回JSON格式数据流
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamData(@RequestParam(required = false, defaultValue = "default") String clientId,
                                 @RequestParam(required = false, defaultValue = "5") int intervalSeconds) {
        SseEmitter emitter = new SseEmitter(60_000L);

        Runnable sender = () -> {
            try {
                Map<String, Object> event = Map.of(
                        "type", "data-update",
                        "clientId", clientId,
                        "data", "Stream message at " + System.currentTimeMillis(),
                        "sequence", System.nanoTime(),
                        "timestamp", System.currentTimeMillis()
                );
                emitter.send(SseEmitter.event().name("data-update").data(event));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        };

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(sender, 0, Math.max(1, intervalSeconds), TimeUnit.SECONDS);
        scheduler.schedule(emitter::complete, 60, TimeUnit.SECONDS);

        // 防止资源泄漏：连接断开时关闭调度器
        emitter.onCompletion(scheduler::shutdown);
        emitter.onTimeout(scheduler::shutdown);

        return emitter;
    }

    // 辅助方法：构建统一响应结构
    private Map<String, Object> buildSuccessResponse(Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "success");
        response.put("data", filterNullValues(data));
        return response;
    }

    // 过滤掉值为null的条目
    private Map<String, Object> filterNullValues(Map<String, Object> map) {
        return map.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);
    }
}