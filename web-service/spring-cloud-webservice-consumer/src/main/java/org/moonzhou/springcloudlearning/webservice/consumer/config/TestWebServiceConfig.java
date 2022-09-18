package org.moonzhou.springcloudlearning.webservice.consumer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author moon zhou
 * @version 1.0
 * @description: test web service connect config
 * @date 2022/9/9 10:24
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "biz.web-service.test")
public class TestWebServiceConfig {

    private String username;
    private String password;
    private String baseAddress;

    private Map<String, String> testServiceWsdl;
    private Map<String, String> testServiceAddress;

    @Getter
    public enum TestServiceWsdlKey {
        GETTEST("getTest"),

        ;

        private String key;

        TestServiceWsdlKey(String key) {
            this.key = key;
        }
    }

    @Getter
    public enum TestServiceAddressKey {
        GETTEST("getTest"),

        ;

        private String key;

        TestServiceAddressKey(String key) {
            this.key = key;
        }
    }

}
