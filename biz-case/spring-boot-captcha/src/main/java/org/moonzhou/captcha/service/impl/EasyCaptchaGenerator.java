package org.moonzhou.captcha.service.impl;

import com.wf.captcha.SpecCaptcha;
import org.moonzhou.captcha.enums.CaptchaType;
import org.moonzhou.captcha.service.CaptchaGenerator;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * EasyCaptcha验证码生成器实现
 */
@Service
public class EasyCaptchaGenerator implements CaptchaGenerator {
    
    @Override
    public String generateCaptcha(OutputStream outputStream, String fingerprint) throws IOException {
        // 创建验证码对象
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));
        
        // 输出验证码图片
        specCaptcha.out(outputStream);
        
        return specCaptcha.text().toLowerCase();
    }
    
    @Override
    public String getCaptchaType() {
        return CaptchaType.EASY.getCode();
    }
}