package org.moonzhou.captcha.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.moonzhou.captcha.enums.CaptchaType;
import org.moonzhou.captcha.service.CaptchaGenerator;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Hutool验证码生成器实现
 */
@Service
public class HutoolCaptchaGenerator implements CaptchaGenerator {
    
    @Override
    public String generateCaptcha(OutputStream outputStream, String fingerprint) throws IOException {
        // 定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 5, 10);
        
        // 设置字体
        lineCaptcha.setFont(new Font("Arial", Font.BOLD, 32));
        
        // 输出验证码图片
        lineCaptcha.write(outputStream);
        
        return lineCaptcha.getCode();
    }
    
    @Override
    public String getCaptchaType() {
        return CaptchaType.HUTOOL.getCode();
    }
}