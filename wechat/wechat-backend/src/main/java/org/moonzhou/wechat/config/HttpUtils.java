package org.moonzhou.wechat.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author moon zhou
 */
@Slf4j
@Component
public class HttpUtils {

    @Resource
    private RestTemplate restTemplate;

    public ResponseEntity<String> getRequest(String uri, Map<String, String> params) {
        String url = generateRequestParameters(uri, params);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
            url, String.class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())){
            log.info("http get success; [ httpCode = {}, url={}, responseBody ={}]",
                    responseEntity.getStatusCodeValue(), url, responseEntity.getBody());
        } else{
            log.info("http get failed; [ httpCode = {}, url={}, responseBody ={}]",
                responseEntity.getStatusCodeValue(), url, responseEntity.getBody());
        }
        return responseEntity;
    }

    public ResponseEntity<String> postRequest(String uri, Map<String, String> urlParams, Map<String, Object> bodyParams) {
        String url = generateRequestParameters(uri, urlParams);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(bodyParams, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);

        if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            log.info("http post success; [ httpCode = {}, url={}, responseBody ={}]",
                    responseEntity.getStatusCodeValue(), url, responseEntity.getBody());
        } else {
            log.info("http post failed; [ httpCode = {}, url={}, responseBody ={}]",
                    responseEntity.getStatusCodeValue(), url, responseEntity.getBody());
        }

        return responseEntity;
    }

    private String generateRequestParameters(String uri,
                                           Map<String, String> params) {
        StringBuilder sb = new StringBuilder().append(uri);
        if (!CollectionUtils.isEmpty(params)) {
            sb.append("?");
            for (Map.Entry<String, String> map : params.entrySet()) {
                sb.append(map.getKey())
                        .append("=")
                        .append(map.getValue())
                        .append("&");
            }
            uri = sb.substring(0, sb.length() - 1);
            return uri;
        }
        return sb.toString();
    }

}
