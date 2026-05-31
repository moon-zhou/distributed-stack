package org.moonzhou.springbootserializer.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.moonzhou.springbootserializer.annotation.EncryptField;
import org.moonzhou.springbootserializer.util.EncryptUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义序列化器
 * 处理带有@EncryptField注解的字段，在序列化时除了原样输出外，还按注解里的suffix作为字段名再输出一次
 */
public class CustomSerializer extends JsonSerializer<Object> {
    
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) {
        try {
            gen.writeStartObject();
            
            Field[] fields = value.getClass().getDeclaredFields();
            Map<String, Object> additionalFields = new HashMap<>();
            
            // 先处理所有字段
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(value);
                
                // 检查字段是否有@EncryptField注解
                if (field.isAnnotationPresent(EncryptField.class)) {
                    EncryptField encryptField = field.getAnnotation(EncryptField.class);
                    
                    // 原样输出
                    gen.writeObjectField(fieldName, fieldValue);
                    
                    // 加密后额外输出
                    if (fieldValue instanceof String) {
                        String encryptedValue = encryptValue((String) fieldValue, encryptField.algorithm());
                        String additionalFieldName = fieldName + encryptField.suffix();
                        additionalFields.put(additionalFieldName, encryptedValue);
                    }
                } else {
                    // 普通字段原样输出
                    gen.writeObjectField(fieldName, fieldValue);
                }
            }
            
            // 输出额外的加密字段
            for (Map.Entry<String, Object> entry : additionalFields.entrySet()) {
                gen.writeObjectField(entry.getKey(), entry.getValue());
            }
            
            gen.writeEndObject();
        } catch (Exception e) {
            throw new RuntimeException("序列化失败: " + e.getMessage(), e);
        }
    }
    
    private String encryptValue(String value, String algorithm) {
        if (value == null) {
            return null;
        }
        
        switch (algorithm.toUpperCase()) {
            case "BASE64":
                return EncryptUtils.base64Encrypt(value);
            case "SIMPLE":
                return EncryptUtils.simpleEncrypt(value);
            default:
                return value; // 不认识的算法不处理
        }
    }
}