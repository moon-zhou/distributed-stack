package org.moonzhou.springcloudlearning.feign.provider.openapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/feign")
public class ProviderDemoController {
    @Value("${server.port}")
    private Integer port;
    
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * http://localhost:8081/spring-cloud-learning/feign/provider/api/v1/feign/test
     * @return
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test from " + applicationName + ", service port=" + port);
    }
}