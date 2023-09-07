package org.moonzhou.transaction.biz.effect;

import lombok.AllArgsConstructor;
import org.moonzhou.transaction.param.UserInfoParam;
import org.moonzhou.transaction.service.UserAuthService;
import org.moonzhou.transaction.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author moon zhou
 * @version 1.0
 * @description:
 * @date 2023/9/7 21:22
 */
@AllArgsConstructor
@Service
@Transactional
public class UserTransactionAnnotationBiz {
    private final UserService userService;

    private final UserAuthService userAuthService;

    /**
     * 无异常，自动事务：均保存成功
     * @param userInfoParam
     * @return
     */
    public Long saveAll(UserInfoParam userInfoParam) {
        Long userId = userService.save(userInfoParam.getUserParam());
        Long userAuthId = userAuthService.save(userInfoParam.getUserAuthParam());
        return userId + userAuthId;
    }

    /**
     * 有事务，第一个保存异常：均保存失败
     * @param userInfoParam
     * @return
     */
    public Long saveAllFailByFirstFail(UserInfoParam userInfoParam) {
        Long userId = userService.saveFail(userInfoParam.getUserParam());
        Long userAuthId = userAuthService.save(userInfoParam.getUserAuthParam());
        return userId + userAuthId;
    }

    /**
     * 有事务，第一个正常保存，第二个保存异常：均保存失败
     * @param userInfoParam
     * @return
     */
    public Long saveAllFailBySecondFail(UserInfoParam userInfoParam) {
        Long userId = userService.save(userInfoParam.getUserParam());
        Long userAuthId = userAuthService.saveFail(userInfoParam.getUserAuthParam());
        return userId + userAuthId;
    }
}


