package org.moonzhou.springcloudlearning.feign.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.moonzhou.springcloudlearning.feign.consumer.client.ProviderDemoClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/feign")
public class ConsumerDemoController {

    private ProviderDemoClient providerDemoClient;

    public ConsumerDemoController(ProviderDemoClient providerDemoClient) {
        this.providerDemoClient = providerDemoClient;
    }

    /**
     * http://localhost:8080/spring-cloud-learning/feign/consumer/api/v1/feign/consumer/hello
     * @return
     */
    @GetMapping("/consumer/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello from service-consumer");
    }

    /**
     * http://localhost:8080/spring-cloud-learning/feign/consumer/api/v1/feign/consumer/feign
     * @return
     */
    @GetMapping("/consumer/feign")
    public ResponseEntity<String> call() {
        log.info(">>>> call service-provider <<<<");
        String result = providerDemoClient.test();
        return ResponseEntity.ok(result);
    }
}