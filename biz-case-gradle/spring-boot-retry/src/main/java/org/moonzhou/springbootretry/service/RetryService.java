package org.moonzhou.springbootretry.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RetryService {

    // 使用@Retryable标记需要重试的方法，并设置重试条件、尝试次数和重试间隔
    @Retryable(include = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    public void serviceMethodThatMayFail() {
        simulateFailure();
    }

    // 模拟可能会失败的操作
    private void simulateFailure() {
        if (Math.random() > 0.5) {
            throw new RuntimeException("服务调用失败");
        }
        System.out.println("服务调用成功");
    }

    // 当达到最大重试次数后执行的恢复方法
    @Recover
    public void recover(Exception e) {
        System.out.println("重试失败，执行恢复操作：" + e.getMessage());
    }
}
