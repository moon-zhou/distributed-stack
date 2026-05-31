package org.moonzhou.distributedstack.springboot4performancetest.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author moon zhou
 * @email
 * @date 2026/1/27 17:22
 **/
@Slf4j
@Service
public class SseEmitterService {

    // 使用 ConcurrentHashMap 存储用户对应的 Emitter
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    // 使用线程池处理异步任务
    private final ThreadPoolTaskExecutor taskExecutor;

    public SseEmitterService(ThreadPoolTaskExecutor sseTaskExecutor) {
        this.taskExecutor = sseTaskExecutor;
    }

    /**
     * 创建 SseEmitter 连接
     */
    public SseEmitter createEmitter(String clientId) {
        // 如果已存在，先移除旧的
        removeEmitter(clientId);

        // 创建新的 Emitter，设置永不超时
        SseEmitter emitter = new SseEmitter(0L);

        // 存储 emitter
        emitters.put(clientId, emitter);

        // 配置回调
        emitter.onCompletion(() -> {
            log.info("SSE连接完成, clientId: {}", clientId);
            emitters.remove(clientId);
        });

        emitter.onTimeout(() -> {
            log.warn("SSE连接超时, clientId: {}", clientId);
            emitters.remove(clientId);
        });

        emitter.onError((e) -> {
            log.error("SSE连接错误, clientId: {}", clientId, e);
            emitters.remove(clientId);
        });

        // 发送连接成功事件
        sendEvent(clientId, "connect", Map.of(
                "message", "连接成功",
                "clientId", clientId,
                "time", LocalDateTime.now()
        ));

        return emitter;
    }

    /**
     * 发送事件到指定客户端
     */
    public void sendEvent(String clientId, String eventName, Object data) {
        SseEmitter emitter = emitters.get(clientId);
        if (emitter == null) {
            log.warn("客户端 {} 未连接", clientId);
            return;
        }

        // 如果发送消息不是大报文，直接在当前线程里发送即可，无需多线程
        taskExecutor.execute(() -> {
            try {
                SseEmitter.SseEventBuilder event = SseEmitter.event()
                        .id(UUID.randomUUID().toString())
                        .name(eventName)
                        .data(data, MediaType.APPLICATION_JSON);

                emitter.send(event);
                log.debug("发送事件到 {}: {}", clientId, eventName);

            } catch (IOException e) {
                log.error("发送事件失败, clientId: {}", clientId, e);
                removeEmitter(clientId);
            }
        });
    }

    /**
     * 广播事件到所有客户端
     */
    public void broadcastEvent(String eventName, Object data) {
        emitters.forEach((clientId, emitter) -> {
            sendEvent(clientId, eventName, data);
        });
    }

    /**
     * 移除 Emitter
     */
    public void removeEmitter(String clientId) {
        SseEmitter removed = emitters.remove(clientId);
        if (removed != null) {
            try {
                removed.complete();
            } catch (Exception e) {
                log.warn("关闭 Emitter 异常", e);
            }
        }
    }

    /**
     * 获取连接数
     */
    public int getConnectionCount() {
        return emitters.size();
    }

    /**
     * 启动心跳任务（可选，在 @PostConstruct 中调用）
     */
    @PostConstruct
    public void startHeartbeat() {
        ScheduledExecutorService heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();
        heartbeatExecutor.scheduleAtFixedRate(() -> {
            emitters.forEach((clientId, emitter) -> {
                try {
                    // 发送注释作为心跳（不会触发 onMessage，但保持连接）
                    emitter.send(SseEmitter.event()
                            .comment("ping")
                            .build());
                } catch (IOException e) {
                    log.warn("心跳发送失败，移除客户端: {}", clientId);
                    removeEmitter(clientId);
                }
            });
        }, 30, 30, TimeUnit.SECONDS); // 每30秒发送一次
    }
}
