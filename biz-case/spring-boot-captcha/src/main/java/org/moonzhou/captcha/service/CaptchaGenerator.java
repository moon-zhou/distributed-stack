package org.moonzhou.captcha.service;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 验证码生成器接口
 */
public interface CaptchaGenerator {
    
    /**
     * 生成验证码图片
     * @param outputStream 输出流
     * @param fingerprint 设备指纹
     * @return 验证码文本
     * @throws IOException IO异常
     */
    String generateCaptcha(OutputStream outputStream, String fingerprint) throws IOException;
    
    /**
     * 获取验证码类型
     * @return 验证码类型
     */
    String getCaptchaType();
}