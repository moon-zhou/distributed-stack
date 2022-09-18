package org.moonzhou.springcloudlearning.webservice.provider.config;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.moonzhou.springcloudlearning.webservice.provider.service.ITestServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @author moon zhou
 * @version 1.0
 * @description: web service server config
 * @date 2022/9/6 13:57
 */
@Configuration
public class CxfConfig {

    @Autowired
    private ITestServer testServer;

    @Bean
    public ServletRegistrationBean cxfServlet() {
        // 对外暴露的服务名
        // 发布后端服务列表地址：http://localhost:8081/spring-cloud-learning/webservice/provider/moonzhou-webservice
        return new ServletRegistrationBean(new CXFServlet(),"/moonzhou-webservice/*");
    }

    /**
     * 采用动态工厂方式 不需要指定服务接口
     * 服务和消费不能在一个应用，此处启动时初始化，依赖了自己，会导致启动失败
     */
    /*@Lazy
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Bean
    public Client dynamicClient() {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8081/spring-cloud-learning/webservice/provider/moonzhou-webservice/getTest");
        return client;
    }*/

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }


    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), testServer);
        // 对外暴露的方法名
        endpoint.publish("/getTest");
        return endpoint;
    }
}
