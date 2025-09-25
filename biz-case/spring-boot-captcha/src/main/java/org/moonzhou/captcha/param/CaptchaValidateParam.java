package org.moonzhou.captcha.param;

import lombok.Data;
import org.moonzhou.captcha.enums.CaptchaType;

import jakarta.validation.constraints.NotBlank;

/**
 * 验证码请求参数
 */
@Data
public class CaptchaValidateParam {
    /**
     * 验证码，不能为空
     */
    @NotBlank(message = "验证码不能为空")
    private String code;
    
    /**
     * 设备ID，不能为空
     */
    @NotBlank(message = "设备ID不能为空")
    private String deviceID;

    /**
     * 验证码类型
     */
    private CaptchaType captchaType;
}