package org.moonzhou.springbootserializer.model;

import lombok.Data;
import org.moonzhou.springbootserializer.annotation.EncryptField;

/**
 * 用户模型类
 */
@Data
public class User {
    
    private Long id;
    
    private String name;
    
    @EncryptField(algorithm = "BASE64", suffix = "Encrypted")
    private String phone;
    
    @EncryptField(algorithm = "SIMPLE", suffix = "Encrypted")
    private String email;
    
    private Integer age;
    
    private Boolean active;
}