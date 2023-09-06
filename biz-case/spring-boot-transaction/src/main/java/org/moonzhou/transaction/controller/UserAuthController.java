package org.moonzhou.transaction.controller;

import org.moonzhou.transaction.common.dto.Result;
import org.moonzhou.transaction.dto.UserAuthDTO;
import org.moonzhou.transaction.service.UserAuthService;
import org.moonzhou.transaction.param.UserAuthParam;
import org.moonzhou.transaction.param.UserAuthQueryParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author moon zhou
 * @since 2023-09-06
 */
@RestController
@RequestMapping("/api/v1/user-auths")
public class UserAuthController {

    private final UserAuthService service;

    public UserAuthController(UserAuthService service) {
        this.service = service;
    }

    @PostMapping
    public Result<Long> save(@Validated @RequestBody UserAuthParam param) {
        Long id = service.save(param);
        return Result.success(id);
    }

    @DeleteMapping("/{id}")
    public Result<Long> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success(id);
    }

    @PutMapping("/{id}")
    public Result<Long> update(@PathVariable Long id, @Validated @RequestBody UserAuthParam param) {
        service.update(id, param);
        return Result.success(id);
    }

    @GetMapping("/{id}")
    public Result<UserAuthDTO> findById(@PathVariable Long id) {
        UserAuthDTO ret = service.getById(id);
        return Result.success(ret);
    }

    @GetMapping
    public Result<List<UserAuthDTO>> list(@Validated UserAuthQueryParam param) {
        List<UserAuthDTO> ret = service.list(param);
        return Result.success(ret);
    }

}
