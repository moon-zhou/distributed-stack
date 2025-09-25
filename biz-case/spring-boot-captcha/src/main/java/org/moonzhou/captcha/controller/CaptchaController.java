package org.moonzhou.captcha.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.moonzhou.captcha.enums.CaptchaType;
import org.moonzhou.captcha.service.CaptchaGenerator;
import org.moonzhou.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 验证码控制器
 * 提供 Hutool 和 EasyCaptcha 两种图形验证码的生成与验证接口
 * 支持 Session 和 Redis 两种存储方式
 * 支持设备指纹识别
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {
    
    @Autowired
    private CaptchaService captchaService;
    
    @Autowired
    private List<CaptchaGenerator> captchaGenerators;
    
    /**
     * Hutool验证码生成接口
     * 访问URL: GET http://localhost:8080/captcha/hutool
     */
    @GetMapping("/hutool")
    public void hutoolCaptcha(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam String deviceID) throws IOException {
        response.setContentType("image/png");
        
        // 查找Hutool验证码生成器
        CaptchaGenerator hutoolGenerator = captchaGenerators.stream()
                .filter(generator -> CaptchaType.HUTOOL.getCode().equals(generator.getCaptchaType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hutool验证码生成器未找到"));
        
        // 生成验证码
        String code = hutoolGenerator.generateCaptcha(response.getOutputStream(), deviceID);
        
        // 存储验证码
        captchaService.storeCaptcha(CaptchaType.HUTOOL.getCode(), code, deviceID);
    }
    
    /**
     * 验证Hutool验证码
     * 访问URL: GET http://localhost:8080/captcha/hutool/validate
     */
    @GetMapping("/hutool/validate")
    @ResponseBody
    public boolean validateHutoolCaptcha(@RequestParam String code,
                                         @RequestParam String deviceID) {
        return captchaService.validateCaptcha(CaptchaType.HUTOOL.getCode(), code, deviceID);
    }
    
    /**
     * EasyCaptcha验证码生成接口
     * 访问URL: GET http://localhost:8080/captcha/easy
     */
    @GetMapping("/easy")
    public void easyCaptcha(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam String deviceID) throws IOException {
        response.setContentType("image/png");
        
        // 查找Easy验证码生成器
        CaptchaGenerator easyGenerator = captchaGenerators.stream()
                .filter(generator -> CaptchaType.EASY.getCode().equals(generator.getCaptchaType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Easy验证码生成器未找到"));
        
        // 生成验证码
        String code = easyGenerator.generateCaptcha(response.getOutputStream(), deviceID);
        
        // 存储验证码
        captchaService.storeCaptcha(CaptchaType.EASY.getCode(), code, deviceID);
    }
    
    /**
     * 验证Easy验证码
     * 访问URL: GET http://localhost:8080/captcha/easy/validate
     */
    @GetMapping("/easy/validate")
    @ResponseBody
    public boolean validateEasyCaptcha(@RequestParam String code,
                                       @RequestParam String deviceID) {
        return captchaService.validateCaptcha(CaptchaType.EASY.getCode(), code, deviceID);
    }
    
}