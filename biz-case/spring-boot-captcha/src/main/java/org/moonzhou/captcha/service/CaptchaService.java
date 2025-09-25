package org.moonzhou.captcha.service;

import jakarta.servlet.http.HttpSession;

public interface CaptchaService {
    
    /**
     * 验证验证码
     * @param session session对象
     * @param captchaType 验证码类型
     * @param inputCode 用户输入的验证码
     * @return 是否验证成功
     */
    boolean validateCaptcha(HttpSession session, String captchaType, String inputCode);
    
    /**
     * 生成验证码并存储到session中
     * @param session session对象
     * @param captchaType 验证码类型
     * @return 生成的验证码
     */
    String generateCaptcha(HttpSession session, String captchaType);
}