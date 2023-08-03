package org.moonzhou.onebyone.enums;

/**
 * @author moon zhou
 * @version 1.0
 * @description: one by one annotation type field value
 * @date 2023/8/2 21:30
 */
public enum OneByOneType {
    /**
     * 用户级防重
     */
    USER_UNREPEATED,

    /**
     * 用户级幂等
     */
    USER_IDEMPOTENT,

    /**
     * 全局限流
     */
    ALL_LIMIT
}
