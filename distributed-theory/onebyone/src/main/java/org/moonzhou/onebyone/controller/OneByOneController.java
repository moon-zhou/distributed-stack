package org.moonzhou.onebyone.controller;

import lombok.extern.slf4j.Slf4j;
import org.moonzhou.onebyone.annotation.OneByOne;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @OneByOne(bizCode = "onebyone:test:index", )
    @RequestMapping("/index")
    public String index() {
        return "hello spring boot one by one test!!!";
    }

}