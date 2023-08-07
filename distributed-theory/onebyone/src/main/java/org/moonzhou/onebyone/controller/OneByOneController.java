package org.moonzhou.onebyone.controller;

import lombok.extern.slf4j.Slf4j;
import org.moonzhou.onebyone.annotation.OneByOne;
import org.moonzhou.onebyone.enums.OneByOneType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author moon zhou
 */
@Slf4j
@RequestMapping("/onebyone")
@RestController
public class OneByOneController {

    /**
     * http://localhost:8080/onebyone/index
     *
     * @return
     */
    @OneByOne(bizCode = "onebyone:test:index", timeout = 3L, timeunit = TimeUnit.SECONDS, message = "try later...", oneByOneType = OneByOneType.USER_UNREPEATED)
    @RequestMapping("/index")
    public String index() {
        return "hello spring boot one by one test!!!";
    }

}