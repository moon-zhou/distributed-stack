package org.moonzhou.captcha.service;

/**
 * 验证码存储服务接口
 */
public interface CaptchaStorageService {
    
    /**
     * 存储验证码
     * @param captchaType 验证码类型
     * @param code 验证码
     * @param deviceID 设备ID
     */
    void storeCaptcha(String captchaType, String code, String deviceID);
    
    /**
     * 获取并移除验证码
     * @param captchaType 验证码类型
     * @param deviceID 设备ID
     * @return 验证码
     */
    String getAndRemoveCaptcha(String captchaType, String deviceID);
    
    /**
     * 获取存储类型
     * @return 存储类型
     */
    String getStorageType();
}