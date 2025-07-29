package org.moonzhou.springbootqrcode.controller;

import com.google.zxing.WriterException;
import org.moonzhou.springbootqrcode.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/qrcode")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    /**
     * 生成二维码接口
     *
     * @param content 二维码内容
     * @param width   二维码宽度（可选，默认300）
     * @param height  二维码高度（可选，默认300）
     * @return 包含二维码Base64字符串的响应
     */
    @GetMapping("/generate")
    public ResponseEntity<Map<String, String>> generateQRCode(
            @RequestParam String content,
            @RequestParam(required = false, defaultValue = "300") int width,
            @RequestParam(required = false, defaultValue = "300") int height) {
        
        Map<String, String> response = new HashMap<>();
        
        try {
            String qrCodeBase64 = qrCodeService.generateQRCode(content, width, height);
            response.put("qrCode", "data:image/png;base64," + qrCodeBase64);
            response.put("message", "二维码生成成功");
            return ResponseEntity.ok(response);
        } catch (WriterException | IOException e) {
            response.put("error", "生成二维码失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 生成二维码并直接返回图片接口
     *
     * @param content 二维码内容
     * @param width   二维码宽度（可选，默认300）
     * @param height  二维码高度（可选，默认300）
     * @return 二维码图片
     */
    @GetMapping(value = "/image", produces = "image/png")
    public ResponseEntity<byte[]> generateQRCodeImage(
            @RequestParam String content,
            @RequestParam(required = false, defaultValue = "300") int width,
            @RequestParam(required = false, defaultValue = "300") int height) {
        
        try {
            String qrCodeBase64 = qrCodeService.generateQRCode(content, width, height);
            byte[] imageBytes = java.util.Base64.getDecoder().decode(qrCodeBase64);
            return ResponseEntity.ok(imageBytes);
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}