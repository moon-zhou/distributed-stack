package org.moonzhou.wechat.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author moon zhou
 * @version 1.0
 * @description: we chat yml config, used for we chat request
 * @date 2023/3/22 21:25
 */
@Getter
@Setter
@EnableConfigurationProperties(WeChatProperties.class)
@ConfigurationProperties(prefix = "wechat")
public class WeChatProperties {
    private String appId;
    private String appSecret;
    private String wxOpenIdUrl;
    private String wxGetTokenUrl;
    private String wxSendMsgTemplateId;
    private String wxSendMsgUrl;
}
