package org.moonzhou.captcha.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.wf.captcha.SpecCaptcha;
import org.moonzhou.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {
    
    @Autowired
    private CaptchaService captchaService;

    /**
     * Hutool验证码生成接口
     * 访问URL: GET http://localhost:8080/captcha/hutool
     */
    @GetMapping("/hutool")
    public void hutoolCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        
        // 定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 5, 10);
        
        // 设置字体
        lineCaptcha.setFont(new Font("Arial", Font.BOLD, 32));
        
        // 将验证码存储到Session中
        HttpSession session = request.getSession();
        session.setAttribute("hutool_captcha", lineCaptcha.getCode());
        
        // 输出验证码图片
        lineCaptcha.write(response.getOutputStream());
    }
    
    /**
     * 验证Hutool验证码
     * 访问URL: GET http://localhost:8080/captcha/hutool/validate
     */
    @GetMapping("/hutool/validate")
    @ResponseBody
    public boolean validateHutoolCaptcha(HttpServletRequest request, @RequestParam String code) {
        return captchaService.validateCaptcha(request.getSession(), "hutool", code);
    }

    /**
     * EasyCaptcha验证码生成接口
     * 访问URL: GET http://localhost:8080/captcha/easy
     */
    @GetMapping("/easy")
    public void easyCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        
        // 创建验证码对象
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));
        
        // 将验证码存储到Session中
        HttpSession session = request.getSession();
        session.setAttribute("easy_captcha", specCaptcha.text().toLowerCase());
        
        // 输出验证码图片
        specCaptcha.out(response.getOutputStream());
    }
    
    /**
     * 验证Easy验证码
     * 访问URL: GET http://localhost:8080/captcha/easy/validate
     */
    @GetMapping("/easy/validate")
    @ResponseBody
    public boolean validateEasyCaptcha(HttpServletRequest request, @RequestParam String code) {
        return captchaService.validateCaptcha(request.getSession(), "easy", code);
    }

}