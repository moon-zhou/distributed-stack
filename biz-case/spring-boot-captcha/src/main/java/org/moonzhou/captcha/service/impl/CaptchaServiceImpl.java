package org.moonzhou.captcha.service.impl;

import org.moonzhou.captcha.enums.CaptchaType;
import org.moonzhou.captcha.enums.StorageType;
import org.moonzhou.captcha.property.CaptchaProperties;
import org.moonzhou.captcha.service.CaptchaService;
import org.moonzhou.captcha.service.CaptchaStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    
    private final List<CaptchaStorageService> captchaStorageServices;
    private final CaptchaProperties captchaProperties;
    
    @Autowired
    public CaptchaServiceImpl(List<CaptchaStorageService> captchaStorageServices,
                              CaptchaProperties captchaProperties) {
        this.captchaStorageServices = captchaStorageServices;
        this.captchaProperties = captchaProperties;
    }
    
    @Override
    public boolean validateCaptcha(String captchaType, String inputCode, String deviceID) {
        if (inputCode == null || captchaType == null) {
            return false;
        }
        
        // 获取默认存储服务
        CaptchaStorageService storageService = getDefaultStorageService();
        
        String storedCode = storageService.getAndRemoveCaptcha(captchaType, deviceID);
        return storedCode != null && storedCode.equalsIgnoreCase(inputCode);
    }
    
    @Override
    public String generateCaptcha(String captchaType, String deviceID) {
        // 生成随机验证码
        String code = UUID.randomUUID().toString().substring(0, 5);
        
        // 获取默认存储服务
        CaptchaStorageService storageService = getDefaultStorageService();
        
        storageService.storeCaptcha(captchaType, code, deviceID);
        return code;
    }
    
    @Override
    public void storeCaptcha(String captchaType, String code, String deviceID) {
        // 获取默认存储服务
        CaptchaStorageService storageService = getDefaultStorageService();
        
        storageService.storeCaptcha(captchaType, code, deviceID);
    }
    
    @Override
    public boolean validateCaptcha(String captchaType, String inputCode) {
        return validateCaptcha(captchaType, inputCode, null);
    }
    
    @Override
    public String generateCaptcha(String captchaType) {
        return generateCaptcha(captchaType, null);
    }
    
    /**
     * 获取默认存储服务
     * @return 存储服务
     */
    private CaptchaStorageService getDefaultStorageService() {
        StorageType storageType = captchaProperties.getStorage();
        
        for (CaptchaStorageService service : captchaStorageServices) {
            if (service.getStorageType().equals(storageType.getCode())) {
                return service;
            }
        }
        
        // 默认返回Session存储服务
        return captchaStorageServices.stream()
                .filter(service -> StorageType.SESSION.getCode().equals(service.getStorageType()))
                .findFirst()
                .orElse(captchaStorageServices.get(0));
    }
}