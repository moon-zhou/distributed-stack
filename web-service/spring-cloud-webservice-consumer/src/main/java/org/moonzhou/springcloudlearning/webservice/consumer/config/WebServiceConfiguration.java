package org.moonzhou.springcloudlearning.webservice.consumer.config;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.moonzhou.springcloudlearning.webservice.consumer.factory.TestJaxWsProxyFactory;
import org.moonzhou.springcloudlearning.webservice.consumer.webservice.test.TestServerImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

/**
 * @author moon zhou
 * @version 1.0
 * @description: web service init bean
 * @date 2022/9/9 10:09
 */
@EnableConfigurationProperties(TestWebServiceConfig.class)
@Configuration
public class WebServiceConfiguration {

    /**
     * Since address and service variable need to be set separately in different scenarios,
     * multiple instances of bean instantiation are required here.
     * @param testWebServiceConfig test config
     * @return
     */
    @Bean
    public TestJaxWsProxyFactory testJaxWsProxyFactory(TestWebServiceConfig testWebServiceConfig) {
        // init test web service method
        JaxWsProxyFactoryBean testJaxWsProxyFactoryBean = initJaxWsProxyFactoryBean(TestWebServiceConfig.TestServiceAddressKey.GETTEST, testWebServiceConfig);
        testJaxWsProxyFactoryBean.setServiceClass(TestServerImpl.class);


        // init factory
        TestJaxWsProxyFactory testJaxWsProxyFactory = new TestJaxWsProxyFactory();
        testJaxWsProxyFactory.init(TestWebServiceConfig.TestServiceAddressKey.GETTEST, testJaxWsProxyFactoryBean);

        return testJaxWsProxyFactory;
    }

    @Bean
    public Client client(TestWebServiceConfig testWebServiceConfig) {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(testWebServiceConfig.getTestServiceWsdl().get(TestWebServiceConfig.TestServiceWsdlKey.GETTEST.getKey()));

        return client;
    }

    @NotNull
    private JaxWsProxyFactoryBean initJaxWsProxyFactoryBean(TestWebServiceConfig.TestServiceAddressKey key, TestWebServiceConfig TestWebServiceConfig) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        // factory.setUsername(TestWebServiceConfig.getUsername());
        // factory.setPassword(TestWebServiceConfig.getPassword());
        factory.setAddress(TestWebServiceConfig.getTestServiceAddress().get(key.getKey()));

        // add request and response log interceptor
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        return factory;
    }
}
