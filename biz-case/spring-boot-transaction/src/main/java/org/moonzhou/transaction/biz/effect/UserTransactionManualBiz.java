package org.moonzhou.transaction.biz.effect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.moonzhou.transaction.common.util.TransactionUtils;
import org.moonzhou.transaction.param.UserInfoParam;
import org.moonzhou.transaction.service.UserAuthService;
import org.moonzhou.transaction.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * @author moon zhou
 * @version 1.0
 * @description:
 * @date 2023/9/7 21:22
 */
@Slf4j
@AllArgsConstructor
@Service
public class UserTransactionManualBiz {
    private final UserService userService;

    private final UserAuthService userAuthService;

    /**
     * 无异常，手动事务：均保存成功
     * @param userInfoParam
     * @return
     */
    public Long saveAll(UserInfoParam userInfoParam) {
        AtomicReference<Long> result = new AtomicReference<>(0L);
        save(userInfoParam, (param) -> {
            result.updateAndGet(v -> v + userService.save(param.getUserParam()));
            result.updateAndGet(v -> v + userAuthService.save(param.getUserAuthParam()));
        });

        return result.get();
    }

    /**
     * 无事务，第一个保存异常：均无法保存
     * @param userInfoParam
     * @return
     */
    public Long saveFailByFirstFail(UserInfoParam userInfoParam) {
        Long userId = userService.saveFail(userInfoParam.getUserParam());
        Long userAuthId = userAuthService.save(userInfoParam.getUserAuthParam());
        return userId + userAuthId;
    }

    /**
     * 无事务，第二个保存异常：第一个保存保存成功，第二个保存失败
     * @param userInfoParam
     * @return
     */
    public Long saveFirstBySecondFail(UserInfoParam userInfoParam) {
        Long userId = userService.save(userInfoParam.getUserParam());
        Long userAuthId = userAuthService.saveFail(userInfoParam.getUserAuthParam());
        return userId + userAuthId;
    }

    /**
     * 有事务，第一个保存异常：均保存失败
     * @param userInfoParam
     * @return
     */
    public Long saveAllFailByFirstFail(UserInfoParam userInfoParam) {
        AtomicReference<Long> result = new AtomicReference<>(0L);
        save(userInfoParam, (param) -> {
            result.updateAndGet(v -> v + userService.saveFail(param.getUserParam()));
            result.updateAndGet(v -> v + userAuthService.save(param.getUserAuthParam()));
        });

        return result.get();
    }

    /**
     * 有事务，第一个正常保存，第二个保存异常：均保存失败
     * @param userInfoParam
     * @return
     */
    public Long saveAllFailBySecondFail(UserInfoParam userInfoParam) {
        AtomicReference<Long> result = new AtomicReference<>(0L);
        save(userInfoParam, (param) -> {
            result.updateAndGet(v -> v + userService.save(param.getUserParam()));
            result.updateAndGet(v -> v + userAuthService.saveFail(param.getUserAuthParam()));
        });

        return result.get();
    }

    private <T> void save(T t, Consumer<T> then) {
        // 手动开启事务
        TransactionStatus transaction = TransactionUtils.begin();

        try {
            then.accept(t);

            // 手动提交事务
            TransactionUtils.commit(transaction);
        } catch (Exception e) {
            log.info("roll back transaction manually...");
            // 手动回滚事务
            TransactionUtils.rollback(transaction);

            throw e;
        }
    }
}


