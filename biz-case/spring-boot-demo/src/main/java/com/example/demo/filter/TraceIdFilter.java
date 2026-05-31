package com.example.demo.filter;

import com.example.demo.util.TraceIdUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TraceIdFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(TraceIdFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TraceIdFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        TraceIdUtil.setTraceId();
        String traceId = TraceIdUtil.getTraceId();

        try {
            log.debug("请求开始 - URI: {}, TraceId: {}", requestURI, traceId);
            chain.doFilter(request, response);
            log.debug("请求结束 - URI: {}, TraceId: {}", requestURI, traceId);
        } catch (Exception e) {
            log.error("请求处理异常 - URI: {}, TraceId: {}", requestURI, TraceIdUtil.getTraceId(), e);
            throw e;
        } finally {
            TraceIdUtil.clear();
        }
    }

    @Override
    public void destroy() {
        log.info("TraceIdFilter 销毁");
    }
}
