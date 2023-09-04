package org.moonzhou.transaction.controller;

import org.moonzhou.transaction.common.dto.Result;
import org.moonzhou.transaction.dto.UserDTO;
import org.moonzhou.transaction.effect.UserService;
import org.moonzhou.transaction.param.UserParam;
import org.moonzhou.transaction.param.UserQueryParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author moon zhou
 * @since 2023-09-01
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public Result<Long> save(@Validated @RequestBody UserParam param) {
        Long id = service.save(param);
        return Result.success(id);
    }

    @DeleteMapping("/{id}")
    public Result<Long> delete(@PathVariable Long id) {
        service.removeById(id);
        return Result.success(id);
    }

    @PutMapping("/{id}")
    public Result<Long> update(@PathVariable Long id, @Validated @RequestBody UserParam param) {
        service.update(id, param);
        return Result.success(id);
    }


    @GetMapping("/{id}")
    public Result<UserDTO> findById(@PathVariable Long id) {
        UserDTO ret = service.getById(id);
        return Result.success(ret);
    }

    @GetMapping
    public Result<List<UserDTO>> list(@Validated UserQueryParam param) {
        List<UserDTO> ret = service.list(param);
        return Result.success(ret);
    }

}
