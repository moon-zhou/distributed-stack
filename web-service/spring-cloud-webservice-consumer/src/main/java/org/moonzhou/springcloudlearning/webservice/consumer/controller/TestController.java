package org.moonzhou.springcloudlearning.webservice.consumer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.moonzhou.springcloudlearning.webservice.consumer.config.TestWebServiceConfig;
import org.moonzhou.springcloudlearning.webservice.consumer.factory.TestJaxWsProxyFactory;
import org.moonzhou.springcloudlearning.webservice.consumer.webservice.test.TestDto;
import org.moonzhou.springcloudlearning.webservice.consumer.webservice.test.TestServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author moon zhou
 * @version 1.0
 * @description: test web service consumer/client
 * @date 2022/9/18 20:21
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/test/webservice")
public class TestController {

    public static final String TEST_STR_FORMAT = "%s_%s";

    /**
     * static
     */
    @Autowired
    private TestJaxWsProxyFactory testJaxWsProxyFactory;

    /**
     * dynamic
     */
    @Autowired
    private Client client;

    /**
     * http://localhost:8080/spring-cloud-learning/webservice/consumer/api/v1/test/webservice/test
     * @return
     */
    @GetMapping("test")
    public String test() {
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = testJaxWsProxyFactory.get(TestWebServiceConfig.TestServiceAddressKey.GETTEST);
        TestServerImpl testServer = jaxWsProxyFactoryBean.create(TestServerImpl.class);
        TestDto testDto = testServer.getTest("moon zhou test web service");

        return String.format(TEST_STR_FORMAT, testDto.getId(), testDto.getName());
    }

    /**
     * http://localhost:8080/spring-cloud-learning/webservice/consumer/api/v1/test/webservice/testDynamic
     * @return
     */
    @GetMapping("testDynamic")
    public String testDynamic() {
        TestDto testDto;
        ObjectMapper mapper = new ObjectMapper();

        try {
            // invoke("方法名",参数1,参数2,参数3....);
            Object[] objects = client.invoke("getTest", "test web service, dynamic");
            log.info(mapper.writeValueAsString(objects[0]));

            testDto = mapper.convertValue(objects[0], TestDto.class);
        } catch (Exception e) {
            testDto = new TestDto();

            log.error("web service consumer test error: ", e);
        }
        return String.format(TEST_STR_FORMAT, testDto.getId(), testDto.getName());
    }
}
