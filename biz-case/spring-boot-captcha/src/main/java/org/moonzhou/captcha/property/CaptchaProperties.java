package org.moonzhou.captcha.property;

import org.moonzhou.captcha.enums.StorageType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * 验证码配置属性类
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {
    
    /**
     * 存储类型，默认为session
     */
    private StorageType storage = StorageType.SESSION;
}