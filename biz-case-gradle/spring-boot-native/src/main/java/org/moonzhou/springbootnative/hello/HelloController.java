// src/main/java/org/moonzhou/springbootnative/HelloController.java
package org.moonzhou.springbootnative.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Spring Boot Native!";
    }

    @GetMapping("/hello-with-name")
    public String sayHelloWithName(@RequestParam(defaultValue = "World") String name) {
        return "Hello, " + name + "! Welcome to Spring Boot Native Demo!";
    }

    @GetMapping("/info")
    public Map<String, Object> getAppInfo() {
        return Map.of(
                "appName", "spring-boot-native",
                "message", "Spring Boot Native Demo Application",
                "timestamp", LocalDateTime.now().toString()
        );
    }
}