package com.example.demo.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ControllerLogAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Pointcut("execution(* com.example.demo.controller..*.*(..))")
    public void controllerPointcut() {}
    
    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        
        String methodName = joinPoint.getSignature().toShortString();
        String requestUrl = request != null ? request.getRequestURL().toString() : "";
        String httpMethod = request != null ? request.getMethod() : "";
        
        Object[] args = joinPoint.getArgs();
        String params = "";
        try {
            params = objectMapper.writeValueAsString(args);
        } catch (Exception e) {
            params = "[Serialization failed]";
        }
        
        logger.info("[CONTROLLER] >>> {} {} - Method: {} - Request Params: {}", 
                httpMethod, requestUrl, methodName, params);
        
        Object result = null;
        try {
            result = joinPoint.proceed();
            long costTime = System.currentTimeMillis() - startTime;
            
            String resultStr = "";
            try {
                resultStr = objectMapper.writeValueAsString(result);
                if (resultStr.length() > 500) {
                    resultStr = resultStr.substring(0, 500) + "...";
                }
            } catch (Exception e) {
                resultStr = "[Serialization failed]";
            }
            
            logger.info("[CONTROLLER] <<< {} {} - Method: {} - Response: {} - Cost: {}ms", 
                    httpMethod, requestUrl, methodName, resultStr, costTime);
            
            return result;
        } catch (Exception e) {
            long costTime = System.currentTimeMillis() - startTime;
            logger.error("[CONTROLLER] !!! {} {} - Method: {} - Error: {} - Cost: {}ms", 
                    httpMethod, requestUrl, methodName, e.getMessage(), costTime);
            throw e;
        }
    }
}
