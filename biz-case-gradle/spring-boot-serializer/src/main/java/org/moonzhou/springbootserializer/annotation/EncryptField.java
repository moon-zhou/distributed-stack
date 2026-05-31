package org.moonzhou.springbootserializer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加密字段注解
 * 用于标记需要在序列化/反序列化过程中进行特殊处理的字段
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptField {
    /**
     * 加密算法类型
     * @return 算法类型
     */
    String algorithm() default "BASE64";
    
    /**
     * 用于出参时额外输出的字段名后缀
     * @return 字段名后缀
     */
    String suffix() default "Encrypted";
}