package org.moonzhou.transaction.controller;

import lombok.AllArgsConstructor;
import org.moonzhou.transaction.common.dto.Result;
import org.moonzhou.transaction.dto.UserDTO;
import org.moonzhou.transaction.param.UserAuthParam;
import org.moonzhou.transaction.param.UserInfoParam;
import org.moonzhou.transaction.param.UserParam;
import org.moonzhou.transaction.param.UserQueryParam;
import org.moonzhou.transaction.service.UserAuthService;
import org.moonzhou.transaction.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author moon zhou
 * @since 2023-09-01
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    private final UserService userService;

    private final UserAuthService userAuthService;

    @PostMapping("saveUserSuccess")
    public Result<Long> saveUser(@Validated @RequestBody UserParam param) {
        Long id = userService.save(param);
        return Result.success(id);
    }

    @PostMapping("saveUserAuthSuccess")
    public Result<Long> saveUserAuth(@Validated @RequestBody UserAuthParam param) {
        Long id = userAuthService.save(param);
        return Result.success(id);
    }

    @PostMapping("saveAll")
    public Result<Long> saveAll(@Validated @RequestBody UserInfoParam userInfoParam) {
        Long userId = userService.save(userInfoParam.getUserParam());
        Long userAuthId = userAuthService.save(userInfoParam.getUserAuthParam());
        return Result.success(userId + userAuthId);
    }


    @PostMapping("saveUserFail")
    public Result<Long> saveUserFail(@Validated @RequestBody UserParam param) {
        Long id = userService.saveFail(param);
        return Result.success(id);
    }

    @PostMapping("saveUserAuthFail")
    public Result<Long> saveUserAuthFail(@Validated @RequestBody UserAuthParam param) {
        Long id = userAuthService.saveFail(param);
        return Result.success(id);
    }

    /**
     * 两个service使用不同的事务，第一个运行时异常，保存失败，第二个未运行。所以都未保存成功。
     * @param userInfoParam
     * @return
     */
    @PostMapping("saveAllFailByFirstFail")
    public Result<Long> saveAllFailByFirstFail(@Validated @RequestBody UserInfoParam userInfoParam) {
        Long userId = userService.saveFail(userInfoParam.getUserParam());
        Long userAuthId = userAuthService.save(userInfoParam.getUserAuthParam());
        return Result.success(userId + userAuthId);
    }

    /**
     * 两个service使用不同的事务，第一个正常运行，保存后持久化到数据库，第二个运行时异常，保存失败，但因为时不同的事务，所以第二个运行异常保存失败，但不影响已经保存的第一个。
     * @param userInfoParam
     * @return
     */
    @PostMapping("saveFirstByLastFail")
    public Result<Long> saveFirstByLastFail(@Validated @RequestBody UserInfoParam userInfoParam) {
        Long userId = userService.save(userInfoParam.getUserParam());
        Long userAuthId = userAuthService.saveFail(userInfoParam.getUserAuthParam());
        return Result.success(userId + userAuthId);
    }
}
