package org.moonzhou.onebyone.annotation;

import org.moonzhou.onebyone.enums.OneByOneType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 业务层级的流量控制，根据类型控制是防重，幂等，还是限流
 *
 * @author moon zhou
 */
@Target(ElementType.METHOD) // 作用到方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
public @interface OneByOne {
    /**
     * 业务场景编码：
     * 保存xxx save-xxx
     * 更新xxx update-xxx
     */
    String bizCode() default "";

    /**
     * 间隔时间(ms)，小于此时间视为重复提交
     */
    long timeout() default 3000L;

    /**
     * 间隔单位，默认ms
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * 提示消息
     */
    String message() default "Please do not resubmit, try again later!";

    /**
     * 类型/模式：默认用户级别的防重
     */
    OneByOneType oneByOneType() default OneByOneType.USER_UNREPEATED;
}