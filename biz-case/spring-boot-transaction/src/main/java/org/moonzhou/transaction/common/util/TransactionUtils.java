package org.moonzhou.transaction.common.util;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * 事务管理器，用于需要手动控制事务的场景
 * @author moon zhou
 */
@Component
public class TransactionUtils {

    private static DataSourceTransactionManager manualTransactionManager;

    /**
     * 从Jdk9开始，JavaEE从Jdk中分离，jdk就移除掉了javax.annotation.jar包的默认集成，从而导致版本不兼容。所以一旦spring项目从JDK8升到高版本，都会出现javax.annotation.Resource无法引用报红。
     * javax.servlet 就变成了 jakarta.servlet, jakarta.annotation。api无法向前兼容。
     *
     * 手动引入javax.annotation-api，但是当Spring版本升级到6.0以上的版本，还是会有问题，还是会有问题，类无法注入。因为Spring也放弃了javax.annotation.Resource注解的支持，而是对jakarta.annotation.Resource注解的支持
     * 所以还是直接使用jakarta.annotation
     */
    @Resource
    private DataSourceTransactionManager transactionManager;

    @PostConstruct
    public void init() {
        manualTransactionManager = transactionManager;
    }

    // 开启事务,传入隔离级别
    public static TransactionStatus begin() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 事物隔离级别，开启新事务 TransactionDefinition.ISOLATION_READ_COMMITTED
        def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 事务传播行为
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 默认事务
        TransactionStatus transaction = manualTransactionManager.getTransaction(def);
        // 将拿到的事务返回进去，才能提交。
        return transaction;
    }

    /**
     * 提交事务
     * @param transaction
     */
    public static void commit(TransactionStatus transaction) {
        manualTransactionManager.commit(transaction);
    }

    /**
     * 回滚事务
     * @param transaction
     */
    public static void rollback(TransactionStatus transaction) {
        manualTransactionManager.rollback(transaction);
    }
}