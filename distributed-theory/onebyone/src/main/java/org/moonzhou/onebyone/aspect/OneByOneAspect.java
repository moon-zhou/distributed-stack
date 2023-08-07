package org.moonzhou.onebyone.aspect;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.moonzhou.onebyone.annotation.OneByOne;
import org.moonzhou.onebyone.enums.ExceptionEnum;
import org.moonzhou.onebyone.enums.OneByOneType;
import org.moonzhou.onebyone.exception.BaseException;
import org.moonzhou.onebyone.utils.RedisService;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@AllArgsConstructor
@Aspect
@Component
@Order(2)
public class OneByOneAspect {

    private static final String ONE_BY_ONE_REDIS_KEY_FORMAT = "%s:%s";
    private static final String FIX_VALUE = "1";

    private final RedisService redisService;

    @Pointcut("@annotation(org.moonzhou.onebyone.annotation.OneByOne)")
    public void onebyone() {
    }

    /**
     * 处理表单重复提交方法
     *
     * @param joinPoint
     * @return
     */
    @Around("onebyone()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // 获取请求IP
        HttpServletRequest request = attributes.getRequest();
        String requestIp = request.getRemoteAddr();
        String url = request.getRequestURI();

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        OneByOne oneByOne = method.getAnnotation(OneByOne.class);
        // in web service, userId can get from login context
        // in rpc service, not http protocol, userId is passed by the caller
        // other param: e.g. ip, url...
        // here is just fixed value
        String key = null;
        long timeout = oneByOne.timeout();
        TimeUnit timeunit = oneByOne.timeunit();
        String message = oneByOne.message();
        OneByOneType oneByOneType = oneByOne.oneByOneType();

        switch (oneByOneType) {
            case IP_LIMIT -> key = String.format(ONE_BY_ONE_REDIS_KEY_FORMAT, oneByOne.bizCode(), requestIp);
            case USER_UNREPEATED -> key = String.format(ONE_BY_ONE_REDIS_KEY_FORMAT, oneByOne.bizCode(), "userId");
            case USER_IDEMPOTENT -> key = String.format(ONE_BY_ONE_REDIS_KEY_FORMAT, oneByOne.bizCode(), "userId");
            case ALL_LIMIT -> key = String.format(ONE_BY_ONE_REDIS_KEY_FORMAT, oneByOne.bizCode(), "");
            default -> key = String.format(ONE_BY_ONE_REDIS_KEY_FORMAT, oneByOne.bizCode(), "");
        }

        // 查询redis判断是否有该值，如果没有，直接继续执行（fixvalue，可以为执行的业务结果）
        if (redisService.tryLock(key, FIX_VALUE, timeout, timeunit)) {
            Object o = joinPoint.proceed();
            return o;
        } else {
            // 如果有，抛出异常或者幂等的情况，拿到缓存的执行结果返回
            log.error("用户重复提交表单 , message : [{}]", message);
            throw new BaseException(ExceptionEnum.HANDLE_TOO_FAST);
        }
    }
}