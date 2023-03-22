package org.moonzhou.wechat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moonzhou.wechat.service.WeChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author moon zhou
 * @version 1.0
 * @description: we chat request
 * @date 2023/3/22 21:37
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wechat")
public class WeChatController {

    private final WeChatService weChatService;

    @GetMapping("/notice/{code}")
    public ResponseEntity<Object> notice(@PathVariable String code) {
        log.info("wechat: notice controller start, {}", code);
        boolean result = weChatService.notice(code);
        log.info("wechat: notice controller end");

        return ResponseEntity.ok(result);
    }
}
