package org.moonzhou.springbootgz.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.moonzhou.springbootgz.dto.UserDto;
import org.moonzhou.springbootgz.param.UserParam;
import org.moonzhou.springbootgz.service.UserService;
import org.moonzhou.springbootgz.util.JsonUtil;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPOutputStream;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
  
    private final UserService userService;
  
    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    /**
     * http://127.0.0.1:80/users/queryBigData
     * 正常返回80K，压缩后11K
     * @return
     */
    @GetMapping("/queryBigData")
    public List<UserDto> queryBigData() {
        return userService.queryBigData();
    }

    /**
     * http://127.0.0.1:80/users/gzip
     * @param response
     * @throws IOException
     */
    @GetMapping("/gzip")
    public void gzip(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("content-encoding", "gzip");
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(response.getOutputStream());
             OutputStreamWriter writer = new OutputStreamWriter(gzipOutputStream, StandardCharsets.UTF_8)) {
            List<UserDto> userDtoList = userService.queryBigData();
            writer.write(JsonUtil.toJson(userDtoList));
        }
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }
  
    @PostMapping
    public UserDto save(@RequestBody UserParam userParam) {
        return userService.save(userParam);
    }
  
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
