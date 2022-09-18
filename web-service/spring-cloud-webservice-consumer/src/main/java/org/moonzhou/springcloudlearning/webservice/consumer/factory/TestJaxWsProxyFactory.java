package org.moonzhou.springcloudlearning.webservice.consumer.factory;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.moonzhou.springcloudlearning.webservice.consumer.config.TestWebServiceConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author moon zhou
 * @version 1.0
 * @description: factory to create JaxWsProxyFactoryBean, init by spring
 * @date 2022/9/9 14:39
 */
public class TestJaxWsProxyFactory {
    /**
     * key is {@link TestWebServiceConfig.TestServiceAddressKey}
     */
    private static final Map<String, JaxWsProxyFactoryBean> testJaxWsProxyFactoryMap = new ConcurrentHashMap<>();

    public JaxWsProxyFactoryBean get(TestWebServiceConfig.TestServiceAddressKey key) {
        assert key != null;
        JaxWsProxyFactoryBean testJaxWsProxyFactoryBean = testJaxWsProxyFactoryMap.get(key.getKey());

        assert testJaxWsProxyFactoryBean != null;
        return testJaxWsProxyFactoryBean;
    }

    public void init(TestWebServiceConfig.TestServiceAddressKey key, JaxWsProxyFactoryBean jaxWsProxyFactoryBean) {
        assert key != null;
        assert jaxWsProxyFactoryBean != null;

        testJaxWsProxyFactoryMap.put(key.getKey(), jaxWsProxyFactoryBean);
    }
}
