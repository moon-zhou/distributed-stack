package org.moonzhou.captcha.service;

/**
 * 验证码服务接口，不依赖具体存储方式（如 HttpSession）
 */
public interface CaptchaService {
    
    /**
     * 验证验证码
     * @param captchaType 验证码类型
     * @param inputCode 用户输入的验证码
     * @param deviceID 设备ID
     * @return 是否验证成功
     */
    boolean validateCaptcha(String captchaType, String inputCode, String deviceID);
    
    /**
     * 生成验证码
     * @param captchaType 验证码类型
     * @param deviceID 设备ID
     * @return 生成的验证码
     */
    String generateCaptcha(String captchaType, String deviceID);
    
    /**
     * 存储验证码
     * @param captchaType 验证码类型
     * @param code 验证码
     * @param deviceID 设备ID
     */
    void storeCaptcha(String captchaType, String code, String deviceID);
    
    /**
     * 验证验证码(兼容旧版本)
     * @param captchaType 验证码类型
     * @param inputCode 用户输入的验证码
     * @return 是否验证成功
     */
    boolean validateCaptcha(String captchaType, String inputCode);
    
    /**
     * 生成验证码(兼容旧版本)
     * @param captchaType 验证码类型
     * @return 生成的验证码
     */
    String generateCaptcha(String captchaType);
}