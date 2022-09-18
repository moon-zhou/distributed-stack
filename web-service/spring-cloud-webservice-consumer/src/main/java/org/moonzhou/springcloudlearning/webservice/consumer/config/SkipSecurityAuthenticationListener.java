package org.moonzhou.springcloudlearning.webservice.consumer.config;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientLifeCycleListener;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * CXF连接HTTPS跳过安全证书配置
 * @author moon zhou
 */
@Configuration
public class SkipSecurityAuthenticationListener implements ClientLifeCycleListener {
    @Override
    public void clientCreated(Client client) {
        if (client.getConduit() instanceof HTTPConduit) {
            HTTPConduit conduit = (HTTPConduit) client.getConduit();

            TLSClientParameters params = conduit.getTlsClientParameters();

            if (params == null) {
                params = new TLSClientParameters();
                conduit.setTlsClientParameters(params);
            }

            params.setTrustManagers(new TrustManager[] {
                    new TrustAllManager() });

            params.setDisableCNCheck(true);
        }
    }

    @Override
    public void clientDestroyed(Client client) {
        // Do Nothing
    }



    private static class TrustAllManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            // Do Nothing
        }


        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            // Do Nothing
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

}