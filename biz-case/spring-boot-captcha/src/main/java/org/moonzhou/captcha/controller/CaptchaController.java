package org.moonzhou.captcha.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.moonzhou.captcha.enums.CaptchaType;
import org.moonzhou.captcha.param.CaptchaGenerateParam;
import org.moonzhou.captcha.param.CaptchaValidateParam;
import org.moonzhou.captcha.service.CaptchaGenerator;
import org.moonzhou.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 验证码控制器
 * 提供 Hutool、EasyCaptcha 和 Tianai 三种图形验证码的生成与验证接口
 * 支持 Session 和 Redis 两种存储方式
 * 支持设备指纹识别
 * 
 * 生成验证码接口:
 * 1. Hutool验证码: [GET] http://localhost:8080/captcha/hutool?deviceID=xxx
 * 2. Easy验证码: [GET] http://localhost:8080/captcha/easy?deviceID=xxx
 * 3. Tianai验证码: [GET] http://localhost:8080/captcha/tianai?deviceID=xxx
 * 
 * 验证验证码接口:
 * 1. Hutool验证码: [GET] http://localhost:8080/captcha/hutool/validate?code=xxxx&deviceID=xxx
 * 2. Easy验证码: [GET] http://localhost:8080/captcha/easy/validate?code=xxxx&deviceID=xxx
 * 3. Tianai验证码: [POST] http://localhost:8080/captcha/tianai/validate (参数通过JSON传递)
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
     * 访问URL: GET http://localhost:8080/captcha/hutool?deviceID=xxx
     */
    @GetMapping("/hutool")
    public void hutoolCaptcha(HttpServletRequest request,
                              HttpServletResponse response,
                              @Valid CaptchaGenerateParam generateParam) throws IOException {
        generateParam.setCaptchaType(CaptchaType.HUTOOL);
        
        generateCaptcha(response, generateParam);
    }
    
    /**
     * 验证Hutool验证码
     * 访问URL: GET http://localhost:8080/captcha/hutool/validate?code=xxxx&deviceID=xxx
     */
    @GetMapping("/hutool/validate")
    @ResponseBody
    public boolean validateHutoolCaptcha(@Valid CaptchaValidateParam validateParam) {
        validateParam.setCaptchaType(CaptchaType.HUTOOL);
        return validateCaptcha(validateParam);
    }
    
    /**
     * EasyCaptcha验证码生成接口
     * 访问URL: GET http://localhost:8080/captcha/easy?deviceID=xxx
     */
    @GetMapping("/easy")
    public void easyCaptcha(HttpServletRequest request,
                            HttpServletResponse response,
                            @Valid CaptchaGenerateParam generateParam) throws IOException {
        generateParam.setCaptchaType(CaptchaType.EASY);
        
        generateCaptcha(response, generateParam);
    }
    
    /**
     * 验证Easy验证码
     * 访问URL: GET http://localhost:8080/captcha/easy/validate?code=xxxx&deviceID=xxx
     */
    @GetMapping("/easy/validate")
    @ResponseBody
    public boolean validateEasyCaptcha(@Valid CaptchaValidateParam validateParam) {
        validateParam.setCaptchaType(CaptchaType.EASY);
        return validateCaptcha(validateParam);
    }
    
    /**
     * Tianai验证码生成接口
     * 访问URL: GET http://localhost:8080/captcha/tianai?deviceID=xxx
     */
    @GetMapping("/tianai")
    public void tianaiCaptcha(HttpServletRequest request, HttpServletResponse response,
                              @Valid CaptchaGenerateParam generateParam) throws IOException {
        generateParam.setCaptchaType(CaptchaType.TIANAI);
        
        response.setContentType("application/json;charset=UTF-8");
        generateCaptcha(response, generateParam);
    }
    
    /**
     * 验证Tianai验证码
     * 访问URL: POST http://localhost:8080/captcha/tianai/validate
     */
    @GetMapping("/tianai/validate")
    public boolean validateTianaiCaptcha(@Valid @RequestBody CaptchaValidateParam param) {
        param.setCaptchaType(CaptchaType.TIANAI);
        return validateCaptcha(param);
    }
    
    /**
     * 统一生成验证码方法
     * @param response HTTP响应
     * @param generateParam 验证码请求参数
     * @throws IOException IO异常
     */
    private void generateCaptcha(HttpServletResponse response, CaptchaGenerateParam generateParam) throws IOException {
        // 查找对应验证码生成器
        CaptchaGenerator captchaGenerator = captchaGenerators.stream()
                .filter(generator -> generateParam.getCaptchaType().getCode().equals(generator.getCaptchaType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("验证码生成器未找到: " + generateParam.getCaptchaType()));
        
        // 生成验证码
        String code = captchaGenerator.generateCaptcha(response.getOutputStream(), generateParam.getDeviceID());
        
        // 存储验证码
        captchaService.storeCaptcha(generateParam.getCaptchaType().getCode(), code, generateParam.getDeviceID());
    }
    
    /**
     * 统一验证验证码方法
     * @param validateParam 验证码请求参数
     * @return 是否验证成功
     */
    private boolean validateCaptcha(CaptchaValidateParam validateParam) {
        return captchaService.validateCaptcha(validateParam.getCaptchaType().getCode(), validateParam.getCode(), validateParam.getDeviceID());
    }
}