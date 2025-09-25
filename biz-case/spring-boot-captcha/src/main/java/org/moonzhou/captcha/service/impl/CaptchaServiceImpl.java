package org.moonzhou.captcha.service.impl;

import jakarta.servlet.http.HttpSession;
import org.moonzhou.captcha.service.CaptchaService;
import org.springframework.stereotype.Service;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    
    @Override
    public boolean validateCaptcha(HttpSession session, String captchaType, String inputCode) {
        if (session == null || inputCode == null || captchaType == null) {
            return false;
        }
        
        String sessionKey = captchaType + "_captcha";
        String sessionCode = (String) session.getAttribute(sessionKey);
        
        // 验证后清除验证码
        session.removeAttribute(sessionKey);
        
        return sessionCode != null && sessionCode.equalsIgnoreCase(inputCode);
    }
    
    @Override
    public String generateCaptcha(HttpSession session, String captchaType) {
        // 此方法主要用于生成验证码并存储到session中
        // 实际的验证码图片生成由Controller处理
        return null;
    }
}