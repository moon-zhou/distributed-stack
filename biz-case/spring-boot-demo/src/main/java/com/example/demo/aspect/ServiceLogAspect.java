package com.example.demo.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLogAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Pointcut("execution(* com.example.demo.service..*.*(..))")
    public void servicePointcut() {}
    
    @Around("servicePointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        Object[] args = joinPoint.getArgs();
        String params = "";
        try {
            params = objectMapper.writeValueAsString(args);
            if (params.length() > 300) {
                params = params.substring(0, 300) + "...";
            }
        } catch (Exception e) {
            params = "[Serialization failed]";
        }
        
        logger.info("[SERVICE] >>> {}.{} - Input: {}", className, methodName, params);
        
        Object result = null;
        try {
            result = joinPoint.proceed();
            long costTime = System.currentTimeMillis() - startTime;
            
            String resultStr = "";
            try {
                resultStr = objectMapper.writeValueAsString(result);
                if (resultStr.length() > 300) {
                    resultStr = resultStr.substring(0, 300) + "...";
                }
            } catch (Exception e) {
                resultStr = "[Serialization failed]";
            }
            
            logger.info("[SERVICE] <<< {}.{} - Output: {} - Cost: {}ms", 
                    className, methodName, resultStr, costTime);
            
            return result;
        } catch (Exception e) {
            long costTime = System.currentTimeMillis() - startTime;
            logger.error("[SERVICE] !!! {}.{} - Error: {} - Cost: {}ms", 
                    className, methodName, e.getMessage(), costTime);
            throw e;
        }
    }
}
