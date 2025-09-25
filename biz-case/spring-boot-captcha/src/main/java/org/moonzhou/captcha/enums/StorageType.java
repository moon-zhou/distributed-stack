package org.moonzhou.captcha.enums;

import lombok.Getter;

/**
 * 存储类型枚举
 */
@Getter
public enum StorageType {
    
    SESSION("session", "Session存储"),
    REDIS("redis", "Redis存储");
    
    private final String code;
    private final String description;
    
    StorageType(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据code获取枚举值
     * @param code 存储类型code
     * @return 存储类型枚举
     */
    public static StorageType fromCode(String code) {
        for (StorageType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的存储类型: " + code);
    }
}