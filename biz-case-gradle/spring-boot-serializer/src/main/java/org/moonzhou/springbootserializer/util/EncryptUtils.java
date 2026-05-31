package org.moonzhou.springbootserializer.util;

import java.util.Base64;

/**
 * 加密工具类
 */
public class EncryptUtils {
    
    /**
     * Base64加密
     * @param value 原始值
     * @return 加密后的值
     */
    public static String base64Encrypt(String value) {
        if (value == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
    
    /**
     * Base64解密
     * @param value 加密值
     * @return 解密后的值
     */
    public static String base64Decrypt(String value) {
        if (value == null) {
            return null;
        }
        return new String(Base64.getDecoder().decode(value));
    }
    
    /**
     * 简单替换加密（将字母向前移动1位）
     * @param value 原始值
     * @return 加密后的值
     */
    public static String simpleEncrypt(String value) {
        if (value == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (Character.isLetter(c)) {
                if (c == 'z') {
                    result.append('a');
                } else if (c == 'Z') {
                    result.append('A');
                } else {
                    result.append((char) (c + 1));
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    /**
     * 简单替换解密（将字母向后移动1位）
     * @param value 加密值
     * @return 解密后的值
     */
    public static String simpleDecrypt(String value) {
        if (value == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (Character.isLetter(c)) {
                if (c == 'a') {
                    result.append('z');
                } else if (c == 'A') {
                    result.append('Z');
                } else {
                    result.append((char) (c - 1));
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}