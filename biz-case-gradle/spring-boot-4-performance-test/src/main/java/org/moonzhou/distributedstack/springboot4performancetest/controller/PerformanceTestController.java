package org.moonzhou.distributedstack.springboot4performancetest.controller;

import lombok.extern.slf4j.Slf4j;
import org.moonzhou.distributedstack.springboot4performancetest.dto.Result;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author moon zhou
 * @date 2025/12/30 10:32
 **/
@Slf4j
@RestController
@RequestMapping("/performance/test")
public class PerformanceTestController {

    /**
     * @param params
     * @param headers
     * @return
     */
    @PostMapping("/test1")
    public Result<Object> test1(@RequestBody Map<String, Object> params, @RequestHeader Map<String, String> headers) {
        long startTime = System.currentTimeMillis();
        params.put("startTime", startTime); // 添加开始时间到参数中以便记录

        HashMap<String, Object> result = new HashMap<>();
        result.put("params", params);
        result.put("headers", headers);

        result.put("flag", "intent");

        // 记录请求信息
        logRequestInfo("test1", params, headers, result);

        return Result.success(result);
    }

    /**
     * @param params
     * @param headers
     * @return
     */
    @PostMapping(value = "/test2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter test2(@RequestBody Map<String, Object> params, @RequestHeader Map<String, String> headers) {
        long startTime = System.currentTimeMillis();
        params.put("startTime", startTime); // 添加开始时间到参数中以便记录

        HashMap<String, Object> result = new HashMap<>();
        result.put("params", params);
        result.put("headers", headers);

        result.put("flag", "assistant");

        // 创建SseEmitter对象，设置超时时间为长期有效
        SseEmitter emitter = new SseEmitter(60_000L); // 60秒超时

        // 添加一些测试数据流
        try {
            emitter.send(SseEmitter.event()
                    .name("init")
                    .data("SSE connection established"));

            TimeUnit.SECONDS.sleep(1); // 模拟处理时间
            log.info("waiting 1...");

            // 模拟发送处理参数的数据
            emitter.send(SseEmitter.event()
                    .name("params-received")
                    .data(result.get("params")));

            TimeUnit.SECONDS.sleep(1); // 模拟处理时间
            log.info("waiting 2...");

            // 模拟发送处理头信息的数据
            emitter.send(SseEmitter.event()
                    .name("headers-received")
                    .data(result.get("headers")));

            TimeUnit.SECONDS.sleep(1); // 模拟处理时间
            log.info("waiting 3...");

            emitter.send(SseEmitter.event()
                    .name("flag-received")
                    .data(result.get("flag")));

            TimeUnit.SECONDS.sleep(1); // 模拟处理时间
            log.info("waiting 4...");

            // 发送完成事件
            emitter.send(SseEmitter.event()
                    .name("completion")
                    .data("Processing completed"));

        } catch (Exception e) {
            log.error("Error sending SSE event", e);
        } finally {
            // 如果发送失败，提前完成并移除
            emitter.complete();
            // 记录请求信息
            logRequestInfo("test2", params, headers, result);
        }

        return emitter;
    }

    /**
     * 记录请求信息、响应结果和耗时
     * 符合单一职责原则(SRP)，专门处理日志记录
     */
    private void logRequestInfo(String methodName, Map<String, Object> params,
                                Map<String, String> headers, Map<String, Object> response) {
        long duration = System.currentTimeMillis() - ((Long) params.getOrDefault("startTime", System.currentTimeMillis()));

        // 构建日志信息
        StringBuilder logMessage = new StringBuilder();
        logMessage.append(methodName).append(" processed in ").append(duration).append("ms\n");
        logMessage.append("Params: ").append(params).append("\n");
        logMessage.append("Headers: ").append(headers).append("\n");
        logMessage.append("Response: ").append(response);

        // 使用slf4j记录器输出
        log.info(logMessage.toString());
    }
}
