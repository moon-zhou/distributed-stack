package org.moonzhou.springbootretry.controller;

import org.moonzhou.springbootretry.service.RetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryController {

    private final RetryService retryService;

    @Autowired
    public RetryController(RetryService retryService) {
        this.retryService = retryService;
    }

    /**
     * http://localhost:8080/trigger
     * @return
     */
    @GetMapping("/trigger")
    public String triggerServiceCall() {
        retryService.serviceMethodThatMayFail();
        return "调用完成";
    }
}
