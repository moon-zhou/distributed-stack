package org.moonzhou.captcha.service.impl;

import org.moonzhou.captcha.enums.StorageType;
import org.moonzhou.captcha.service.CaptchaStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redis的验证码存储服务实现
 */
@Service
public class RedisCaptchaStorageServiceImpl implements CaptchaStorageService {
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Override
    public void storeCaptcha(String captchaType, String code, String deviceID) {
        String key = generateRedisKey(captchaType, deviceID);
        stringRedisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES); // 5分钟有效期
    }
    
    @Override
    public String getAndRemoveCaptcha(String captchaType, String deviceID) {
        String key = generateRedisKey(captchaType, deviceID);
        String code = stringRedisTemplate.opsForValue().get(key);
        if (code != null) {
            stringRedisTemplate.delete(key);
        }
        return code;
    }
    
    @Override
    public String getStorageType() {
        return StorageType.REDIS.getCode();
    }
    
    /**
     * 生成Redis存储key
     * @param captchaType 验证码类型
     * @param deviceID 设备ID
     * @return Redis key
     */
    private String generateRedisKey(String captchaType, String deviceID) {
        if (StringUtils.hasText(deviceID)) {
            return "captcha:" + captchaType + ":" + deviceID;
        } else {
            return "captcha:" + captchaType;
        }
    }
}