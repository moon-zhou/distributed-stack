package org.moonzhou.springcloudlearning.webservice.provider.service;

import org.moonzhou.springcloudlearning.webservice.provider.dto.TestDto;

/**
 * @author moon zhou
 * @version 1.0
 * @description: web service 服务接口
 * @date 2022/9/6 13:49
 */
public interface ITestServer {

    TestDto getTest(String name);
}
