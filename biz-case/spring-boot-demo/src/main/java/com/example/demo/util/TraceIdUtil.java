package com.example.demo.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.MDC;

import java.util.UUID;

public class TraceIdUtil {

    private static final String TRACE_ID_KEY = "traceId";

    private static final TransmittableThreadLocal<String> TRACE_ID = new TransmittableThreadLocal<>();

    public static void setTraceId() {
        String traceId = UUID.randomUUID().toString();
        TRACE_ID.set(traceId);
        MDC.put(TRACE_ID_KEY, traceId);
    }

    public static String getTraceId() {
        String traceId = TRACE_ID.get();
        return traceId != null ? traceId : MDC.get(TRACE_ID_KEY);
    }

    public static void clear() {
        TRACE_ID.remove();
        MDC.remove(TRACE_ID_KEY);
    }

    /**
     * 在子线程中初始化 MDC
     * 用于异步线程从 TTL 中获取 traceId 并设置到 MDC
     */
    public static void initMDCInChildThread() {
        String traceId = TRACE_ID.get();
        if (traceId != null) {
            MDC.put(TRACE_ID_KEY, traceId);
        }
    }
}
