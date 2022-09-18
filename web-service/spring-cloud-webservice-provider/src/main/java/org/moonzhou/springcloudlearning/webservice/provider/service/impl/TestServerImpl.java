package org.moonzhou.springcloudlearning.webservice.provider.service.impl;

import org.moonzhou.springcloudlearning.webservice.provider.service.ITestServer;
import org.moonzhou.springcloudlearning.webservice.provider.dto.TestDto;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @author moon zhou
 * @version 1.0
 * @description: web service provider
 * @date 2022/9/6 13:53
 */
@Service
@WebService
public class TestServerImpl implements ITestServer {
    @Override
    public TestDto getTest(String name) {

        return new TestDto(1L, name);
    }
}
