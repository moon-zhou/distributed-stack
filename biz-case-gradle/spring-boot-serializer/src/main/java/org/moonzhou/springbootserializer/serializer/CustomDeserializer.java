package org.moonzhou.springbootserializer.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.moonzhou.springbootserializer.annotation.EncryptField;
import org.moonzhou.springbootserializer.util.EncryptUtils;

import java.lang.reflect.Field;

/**
 * 自定义反序列化器
 * 处理带有@EncryptField注解的字段，在反序列化时自动解密
 */
public class CustomDeserializer extends JsonDeserializer<User> {
    
    private final Class<User> clazz;
    
    public CustomDeserializer(Class<User> clazz) {
        this.clazz = clazz;
        System.out.println("CustomDeserializer initialized for class: " + clazz.getName());
    }
    
    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) {
        try {
            JsonNode node = p.getCodec().readTree(p);
            Object obj = clazz.getDeclaredConstructor().newInstance();
            
            // 遍历类的所有字段
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                
                // 检查字段是否有@EncryptField注解
                if (field.isAnnotationPresent(EncryptField.class)) {
                    EncryptField encryptField = field.getAnnotation(EncryptField.class);
                    JsonNode fieldValue = node.get(fieldName);
                    
                    if (fieldValue != null && !fieldValue.isNull()) {
                        String encryptedValue = fieldValue.asText();
                        String decryptedValue = decryptValue(encryptedValue, encryptField.algorithm());
                        if (String.class.equals(field.getType())) {
                            field.set(obj, decryptedValue);
                        }
                    }
                } else {
                    // 普通字段处理
                    JsonNode fieldValue = node.get(fieldName);
                    if (fieldValue != null) {
                        if (String.class.equals(field.getType()) && fieldValue.isTextual()) {
                            field.set(obj, fieldValue.asText());
                        } else if (Integer.class.equals(field.getType()) || int.class.equals(field.getType())) {
                            field.set(obj, fieldValue.asInt());
                        } else if (Long.class.equals(field.getType()) || long.class.equals(field.getType())) {
                            field.set(obj, fieldValue.asLong());
                        } else if (Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())) {
                            field.set(obj, fieldValue.asBoolean());
                        }
                        // 可以根据需要添加更多类型支持
                    }
                }
            }
            
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("反序列化失败: " + e.getMessage(), e);
        }
    }
    
    private String decryptValue(String value, String algorithm) {
        switch (algorithm.toUpperCase()) {
            case "BASE64":
                return EncryptUtils.base64Decrypt(value);
            case "SIMPLE":
                return EncryptUtils.simpleDecrypt(value);
            default:
                return value; // 不认识的算法不处理
        }
    }
}