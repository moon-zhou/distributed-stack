package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.AsyncService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AsyncService asyncService;
    
    @GetMapping
    public Result<List<User>> list() {
        return Result.success(userService.listAll());
    }
    
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }
    
    @GetMapping("/search")
    public Result<List<User>> search(@RequestParam String name) {
        return Result.success(userService.listByName(name));
    }
    
    @PostMapping
    public Result<Boolean> save(@RequestBody User user) {
        return Result.success(userService.saveUser(user));
    }
    
    @PutMapping
    public Result<Boolean> update(@RequestBody User user) {
        return Result.success(userService.updateUser(user));
    }
    
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(userService.deleteById(id));
    }
    
    @PostMapping("/async-test")
    public Result<String> asyncTest() {
        String traceIdBeforeAsync = org.slf4j.MDC.get("traceId");
        log.info("接收到异步测试请求，调用异步任务之前，TraceID: {}", traceIdBeforeAsync);
        
        for (int i = 0; i < 3; i++) {
            log.debug("提交异步任务 parallelTask({}), 当前 TraceID: {}", i, org.slf4j.MDC.get("traceId"));
            asyncService.parallelTask(i);
        }
        
        log.debug("提交异步任务 asyncTask(完整任务测试), 当前 TraceID: {}", org.slf4j.MDC.get("traceId"));
        asyncService.asyncTask("完整任务测试");
        
        log.info("所有异步任务已提交，TraceID: {}", org.slf4j.MDC.get("traceId"));
        return Result.success("异步任务已提交，请查看日志确认 TraceID 是否传递");
    }
}
