package org.moonzhou.wechat.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moonzhou.wechat.config.Cache;
import org.moonzhou.wechat.config.HttpUtils;
import org.moonzhou.wechat.config.WeChatProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author moon zhou
 * @version 1.0
 * @description: we chat service
 * @date 2023/3/22 21:38
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WeChatService {

    private static final String WE_CHAT_ACCESS_TOKEN_KEY = "wechat_access_token";
    private static final String WE_CHAT_MSG_FORMAT = "hello, test we chat micro program msg!";

    private final WeChatProperties weChatProperties;
    private final HttpUtils httpUtils;

    public boolean notice(String code) {

        // 1. get we chat account openId by code from tencent
        Map<String, Object> wechatUserInfo = generateOpenId(code);
        String openId = MapUtil.getStr(wechatUserInfo, "openId");
        log.info("we chat info, code: {}, openId: {}.", code, openId);

        // 2. get we chat open api access token, wechat doc: https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-access-token/getAccessToken.html
        String accessToken = getWeChatAccessToken();

        // 3. send we chat msg by tencent open api, wechat doc: https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-message-management/subscribe-message/sendMessage.html
        boolean result = sendMsg(accessToken, openId);

        return result;
    }

    public Map<String, Object> generateOpenId(String code) {
        Map<String, String> param = new HashMap<>();
        param.put("appid", weChatProperties.getAppId());
        param.put("secret", weChatProperties.getAppSecret());
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");
        /*
         * 访问腾讯接口，获取open_id和sessionKey
         */
        ResponseEntity<String> response = httpUtils.getRequest(weChatProperties.getWxOpenIdUrl(), param);

        JSONObject jsonResponse = JSONUtil.parseObj(response.getBody());
        if (!HttpStatus.OK.equals(response.getStatusCode()) ||
                jsonResponse.get("errcode") != null) {
            throw new RuntimeException("get we chat openId error!");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("openId", jsonResponse.get("openid"));
        result.put("sessionkey", jsonResponse.get("session_key"));
        return result;
    }

    private String getWeChatAccessToken() {

        // String accessToken = redisService.getStr(WE_CHAT_ACCESS_TOKEN_KEY);
        String accessToken = Cache.getInstance().getCache().get(WE_CHAT_ACCESS_TOKEN_KEY);
        if (!StringUtils.hasLength(accessToken)) {
            Map<String,String> param = MapUtil.newHashMap();
            param.put("appid", weChatProperties.getAppId());
            param.put("secret", weChatProperties.getAppSecret());
            param.put("grant_type", "client_credential");

            ResponseEntity<String> response = httpUtils.getRequest(weChatProperties.getWxGetTokenUrl(), param);
            JSONObject jsonResponse = JSONUtil.parseObj(response.getBody());

            accessToken = jsonResponse.getStr("access_token");
            int expire = jsonResponse.getInt("expires_in");

            // expire prematurely, force refresh
            Long actualExpire = Long.valueOf(expire - 100);
            //redisService.setStr(WE_CHAT_ACCESS_TOKEN_KEY, accessToken, actualExpire);
            Cache.getInstance().getCache().put(WE_CHAT_ACCESS_TOKEN_KEY, accessToken, actualExpire);
        }

        log.info("we chat access token: {}.", accessToken);

        return accessToken;
    }

    private boolean sendMsg(String accessToken, String openId) {

        Map<String,String> urlParam = MapUtil.newHashMap();
        urlParam.put("access_token", accessToken);

        Map<String, Object> data = MapUtil.newHashMap();
        Map<String, Object> data1 = MapUtil.newHashMap();
        data1.put("value", "moon zhou");
        Map<String, Object> data2 = MapUtil.newHashMap();
        data2.put("value", WE_CHAT_MSG_FORMAT);

        data.put("thing12", data1);
        data.put("thing9", data2);

        Map<String, Object> bodyParam = MapUtil.newHashMap();
        bodyParam.put("template_id", weChatProperties.getWxSendMsgTemplateId());
        bodyParam.put("touser", openId);
        bodyParam.put("data", data);
        bodyParam.put("miniprogram_state", "trial"); // 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
        bodyParam.put("lang", "zh_CN"); // default language


        ResponseEntity<String> sendResult = httpUtils.postRequest(weChatProperties.getWxSendMsgUrl(), urlParam, bodyParam);
        JSONObject jsonResponse = JSONUtil.parseObj(sendResult.getBody());
        String errorCode = jsonResponse.getStr("errcode");

        boolean success = "0".equals(errorCode);
        log.info("we chat send msg success: {}.", success);

        return success;
    }
}
