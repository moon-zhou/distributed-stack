package org.moonzhou.transaction.controller;

import lombok.AllArgsConstructor;
import org.moonzhou.transaction.biz.effect.UserTransactionManualBiz;
import org.moonzhou.transaction.common.dto.Result;
import org.moonzhou.transaction.param.UserInfoParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author moon zhou
 * @since 2023-09-01
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/test/biz/manual")
public class TestManualBizController {

    private final UserTransactionManualBiz userTransactionManualBiz;

    /**
     * 直接保存两个表
     * @param userInfoParam
     * @return
     */
    @PostMapping("saveAll")
    public Result<Long> saveAll(@Validated @RequestBody UserInfoParam userInfoParam) {
        return Result.success(userTransactionManualBiz.saveAll(userInfoParam));
    }

    /**
     * 两个service使用相同的事务，第一个运行时异常，保存失败，第二个未运行。所以都未保存成功。
     *
     * @param userInfoParam
     * @return
     */
    @PostMapping("saveAllFailByFirstFail")
    public Result<Long> saveAllFailByFirstFail(@Validated @RequestBody UserInfoParam userInfoParam) {
        return Result.success(userTransactionManualBiz.saveAllFailByFirstFail(userInfoParam));
    }

    /**
     * 两个service使用相同的事务，第一个正常运行，保存后未持久化到数据库，第二个运行时异常，保存失败，所以全部回滚，都未保存成功。
     *
     * @param userInfoParam
     * @return
     */
    @PostMapping("saveAllFailBySecondFail")
    public Result<Long> saveAllFailBySecondFail(@Validated @RequestBody UserInfoParam userInfoParam) {
        return Result.success(userTransactionManualBiz.saveAllFailBySecondFail(userInfoParam));
    }

    /**
     * 无事务，第一个保存异常：均无法保存
     * @param userInfoParam
     * @return
     */
    @PostMapping("saveFailByFirstFail")
    public Result<Long> saveFailByFirstFail(@Validated @RequestBody UserInfoParam userInfoParam) {
        return Result.success(userTransactionManualBiz.saveFailByFirstFail(userInfoParam));
    }

    /**
     * 无事务，第二个保存异常：第一个保存保存成功，第二个保存失败
     * @param userInfoParam
     * @return
     */
    @PostMapping("saveFirstBySecondFail")
    public Result<Long> saveFirstBySecondFail(@Validated @RequestBody UserInfoParam userInfoParam) {
        return Result.success(userTransactionManualBiz.saveFirstBySecondFail(userInfoParam));
    }
}
