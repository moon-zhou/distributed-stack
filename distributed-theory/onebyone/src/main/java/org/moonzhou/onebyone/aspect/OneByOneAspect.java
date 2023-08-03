package org.moonzhou.onebyone.aspect;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.moonzhou.onebyone.enums.ExceptionEnum;
import org.moonzhou.onebyone.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
@Order(2)
public class OneByOneAspect {


    private final Long DEFAULT_TIME = 7L;
    private final String FORM_COMMENT = "FORM_";
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("@annotation(org.moonzhou.onebyone.annotation.OneByOne)")
    public void onebyone() {

    }

    /**
     * 处理表单重复提交方法
     *
     * @param point
     * @return
     */
    @Around("onebyone()")
    public Object arround(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // 获取请求IP
        String requestIp = attributes.getRequest().getRemoteAddr();

        // 查询redis判断是否有该值，如果没有，直接继续执行，
        if (StringUtils.isBlank(redisTemplate.opsForValue().get(FORM_COMMENT + requestIp))) {
            Object o = point.proceed();
            redisTemplate.opsForValue().set(FORM_COMMENT + requestIp, requestIp, DEFAULT_TIME, TimeUnit.SECONDS);
            return o;
        } else {
            // 如果有。抛出异常
            log.error("用户重复提交表单 , IP : [{}]", requestIp);
            throw new BaseException(ExceptionEnum.HANDLE_TOO_FAST);
        }
    }
}