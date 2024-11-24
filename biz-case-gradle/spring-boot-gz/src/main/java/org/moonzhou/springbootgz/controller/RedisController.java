package org.moonzhou.springbootgz.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.moonzhou.springbootgz.service.UserService;
import org.moonzhou.springbootgz.util.JsonUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

/**
 * @author moon zhou
 * @description 本类测试时，需要关闭yml里面配置的gzip
 */
@AllArgsConstructor
@RestController
@RequestMapping("/users/redis")
public class RedisController {

    public static final String REDIS_KEY_GZIP_NO = "moonzhou:GZIP:no";
    public static final String REDIS_KEY_GZIP_YES = "moonzhou:GZIP:yes";
    private final UserService userService;
    private final RedissonClient redissonClient;

    /**
     * http://127.0.0.1:80/users/redis/queryBigData
     * 正常返回80K，压缩后11K
     *
     * @return
     */
    @GetMapping("/queryBigData")
    public void queryBigData(HttpServletResponse response) throws IOException {
        RBucket<String> bucket = redissonClient.getBucket(REDIS_KEY_GZIP_NO);
        String data = bucket.get();
        if (data == null) {
            bucket.set(JsonUtil.toJson(userService.queryBigData()), 100L, TimeUnit.SECONDS);
            data = bucket.get();
        }
        response.setContentType("application/json");

        // 获取输出流
        PrintWriter out = response.getWriter();

        // 输出 JSON 数据
        out.print(data);

        // 关闭输出流
        out.flush();
        out.close();
    }

    /**
     * http://127.0.0.1:80/users/redis/gzip
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/gzip")
    public void gzip(HttpServletResponse response) throws IOException {
        RBucket<byte[]> bucket = redissonClient.getBucket(REDIS_KEY_GZIP_YES);
        byte[] data = bucket.get();

        if (data == null) {
            String json = JsonUtil.toJson(userService.queryBigData());

            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
                // IOUtils.write(json, gzipOutputStream, String.valueOf(StandardCharsets.UTF_8));

                gzipOutputStream.write(json.getBytes(StandardCharsets.UTF_8));
                gzipOutputStream.finish();

                data = byteArrayOutputStream.toByteArray();
                bucket.set(data, 100L, TimeUnit.SECONDS);
                // 重新获取缓存的值
                data = bucket.get();
            }
        }

        response.setContentType("application/json;charset=utf-8");
        response.setHeader("content-encoding", "gzip");
        response.getOutputStream().write(data);
    }
}
