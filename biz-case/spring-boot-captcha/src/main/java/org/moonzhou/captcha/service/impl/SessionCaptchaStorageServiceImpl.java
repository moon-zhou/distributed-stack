package org.moonzhou.captcha.service.impl;

import jakarta.servlet.http.HttpSession;
import org.moonzhou.captcha.enums.StorageType;
import org.moonzhou.captcha.service.CaptchaStorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 基于Session的验证码存储服务实现
 */
@Service
public class SessionCaptchaStorageServiceImpl implements CaptchaStorageService {
    
    @Override
    public void storeCaptcha(String captchaType, String code, String deviceID) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpSession session = attributes.getRequest().getSession();
            String sessionKey = generateSessionKey(captchaType, deviceID);
            session.setAttribute(sessionKey, code);
        }
    }
    
    @Override
    public String getAndRemoveCaptcha(String captchaType, String deviceID) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpSession session = attributes.getRequest().getSession();
            String sessionKey = generateSessionKey(captchaType, deviceID);
            String code = (String) session.getAttribute(sessionKey);
            session.removeAttribute(sessionKey);
            return code;
        }
        return null;
    }
    
    @Override
    public String getStorageType() {
        return StorageType.SESSION.getCode();
    }
    
    /**
     * 生成Session存储key
     * @param captchaType 验证码类型
     * @param deviceID 设备ID
     * @return Session key
     */
    private String generateSessionKey(String captchaType, String deviceID) {
        if (StringUtils.hasText(deviceID)) {
            return captchaType + "_captcha_" + deviceID;
        } else {
            return captchaType + "_captcha";
        }
    }
}