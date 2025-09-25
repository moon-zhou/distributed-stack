package org.moonzhou.captcha.enums;

import lombok.Getter;

/**
 * 验证码类型枚举
 */
@Getter
public enum CaptchaType {
    
    HUTOOL("hutool", "Hutool验证码"),
    EASY("easy", "EasyCaptcha验证码"),
    TIANAI("tianai", "Tianai验证码");
    
    private final String code;
    private final String description;
    
    CaptchaType(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据code获取枚举值
     * @param code 验证码类型code
     * @return 验证码类型枚举
     */
    public static CaptchaType fromCode(String code) {
        for (CaptchaType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的验证码类型: " + code);
    }
}