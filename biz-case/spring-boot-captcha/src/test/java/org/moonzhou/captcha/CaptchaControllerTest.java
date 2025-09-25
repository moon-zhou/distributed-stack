package org.moonzhou.captcha;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CaptchaControllerTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testHutoolCaptchaGeneration() {
        String url = "http://localhost:" + port + "/captcha/hutool";
        
        // 测试生成验证码图片
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getHeaders().getContentType().toString()).contains("image/png");
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    @Test
    public void testEasyCaptchaGeneration() {
        String url = "http://localhost:" + port + "/captcha/easy";
        
        // 测试生成验证码图片
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getHeaders().getContentType().toString()).contains("image/png");
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }
}