package org.moonzhou.springbootserializer.controller;

import lombok.extern.slf4j.Slf4j;
import org.moonzhou.springbootserializer.model.User;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * 
 * 测试路径: http://localhost:8080
 * 
 * 1. GET /user - 获取示例用户信息
 * 2. POST /user - 创建用户，测试反序列化（注解字段解密）
 * 3. PUT /user - 更新用户信息
 * 
 * @author moonzhou
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    
    /**
     * 获取用户信息
     * 测试序列化功能：带@EncryptField注解的字段会额外输出加密字段
     * 
     * 测试URL: GET http://localhost:8080/user
     * 
     * @return 用户信息
     */
    @GetMapping
    public User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setPhone("13812345678");
        user.setEmail("zhangsan@example.com");
        user.setAge(25);
        user.setActive(true);
        return user;
    }
    
    /**
     * 创建用户
     * 测试反序列化功能：带@EncryptField注解的字段会自动解密
     * 
     * 测试URL: POST http://localhost:8080/user
     * 请求体示例:
     * {
     *   "id": 2,
     *   "name": "李四",
     *   "phone": "MTM4ODc2NTQzMjE=",  // BASE64加密的"13887654321"
     *   "email": "kibhboqmf!fybnqmf/dpn",  // SIMPLE加密的"lchang@sample.com"
     *   "age": 30,
     *   "active": true
     * }
     * 
     * @param user 用户信息
     * @return 创建的用户信息
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("接收到的用户信息: {}", user);
        // 在实际应用中，这里会保存到数据库
        return user;
    }
    
    /**
     * 更新用户信息
     * 测试序列化和反序列化功能
     * 
     * 测试URL: PUT http://localhost:8080/user
     * 请求体示例:
     * {
     *   "id": 1,
     *   "name": "张三丰",
     *   "phone": "MTM4MTIzNDU2Nzg=",  // BASE64加密的"13812345678"
     *   "email": "zhangsan!fybnqmf/dpn",  // SIMPLE加密的"zhangsan@example.com"
     *   "age": 26,
     *   "active": false
     * }
     * 
     * @param user 用户信息
     * @return 更新后的用户信息
     */
    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("更新用户信息: {}", user);
        // 在实际应用中，这里会更新数据库记录
        return user;
    }
}