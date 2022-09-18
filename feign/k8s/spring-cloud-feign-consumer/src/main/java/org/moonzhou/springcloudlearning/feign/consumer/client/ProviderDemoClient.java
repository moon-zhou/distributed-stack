package org.moonzhou.springcloudlearning.feign.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * name unique
 * url: service name replace localhost/ip, in k8s
 */
// @FeignClient(name = "${service.provider.name}", url="http://localhost:8080/system-context/")
@FeignClient(name = "${service.provider.name}", url="http://localhost:8081/spring-cloud-learning/feign/provider")
public interface ProviderDemoClient {

    @GetMapping("/api/v1/feign/test")
    String test();
}