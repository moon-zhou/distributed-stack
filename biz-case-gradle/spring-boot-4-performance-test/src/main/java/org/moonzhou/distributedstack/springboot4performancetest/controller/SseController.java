package org.moonzhou.distributedstack.springboot4performancetest.controller;

import lombok.extern.slf4j.Slf4j;
import org.moonzhou.distributedstack.springboot4performancetest.service.SseEmitterService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author moon zhou
 */
@RestController
@RequestMapping("/api/sse")
@CrossOrigin(origins = "*") // 生产环境请限制具体域名
@Slf4j
public class SseController {
    
    private final SseEmitterService sseEmitterService;
    
    public SseController(SseEmitterService sseEmitterService) {
        this.sseEmitterService = sseEmitterService;
    }
    
    /**
     * 建立 SSE 连接
     * GET /api/sse/connect?clientId=xxx
     */
    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@RequestParam String clientId) {
        log.info("客户端 {} 请求建立 SSE 连接", clientId);
        return sseEmitterService.createEmitter(clientId);
    }
    
    /**
     * 断开 SSE 连接
     */
    @PostMapping("/disconnect")
    public ResponseEntity<?> disconnect(@RequestParam String clientId) {
        sseEmitterService.removeEmitter(clientId);
        return ResponseEntity.ok("已断开连接");
    }
    
    /**
     * 发送消息给指定客户端（测试用）
     */
    @PostMapping("/send")
    public ResponseEntity<?> sendToClient(
            @RequestParam String clientId,
            @RequestBody String message) {
        
        sseEmitterService.sendEvent(clientId, "message", Map.of(
            "content", message,
            "time", LocalDateTime.now()
        ));
        return ResponseEntity.ok("消息已发送");
    }
    
    /**
     * 广播消息（测试用）
     */
    @PostMapping("/broadcast")
    public ResponseEntity<?> broadcast(@RequestBody String message) {
        sseEmitterService.broadcastEvent("broadcast", Map.of(
            "content", message,
            "time", LocalDateTime.now()
        ));
        return ResponseEntity.ok("广播已发送");
    }
    
    /**
     * 获取连接统计
     */
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        return ResponseEntity.ok(Map.of(
            "connections", sseEmitterService.getConnectionCount()
        ));
    }
}