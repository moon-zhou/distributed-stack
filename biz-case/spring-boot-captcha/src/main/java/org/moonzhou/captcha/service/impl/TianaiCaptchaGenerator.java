package org.moonzhou.captcha.service.impl;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.application.vo.CaptchaResponse;
import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.moonzhou.captcha.enums.CaptchaType;
import org.moonzhou.captcha.service.CaptchaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Tianai验证码生成器实现
 */
@Service
public class TianaiCaptchaGenerator implements CaptchaGenerator {
    
    @Autowired
    private ImageCaptchaApplication imageCaptchaApplication;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public String generateCaptcha(OutputStream outputStream, String fingerprint) throws IOException {
        // 生成验证码
        CaptchaResponse<ImageCaptchaVO> response = imageCaptchaApplication.generateCaptcha();
        
        // 将验证码信息转换为JSON并写入输出流
        String captchaJson = objectMapper.writeValueAsString(response);
        outputStream.write(captchaJson.getBytes());
        
        // 返回验证码ID用于验证
        return response.getId();
    }
    
    @Override
    public String getCaptchaType() {
        return CaptchaType.TIANAI.getCode();
    }
}