package org.moonzhou.springbootcommandline.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

// 使用@ConfigurationProperties注解指定前缀，表示所有以coder.academy开头的属性都会映射到这个类中
@ConfigurationProperties(prefix = "command.line")
public class CommandLineProperties {
    private String name = "moon-zhou";
    private int age = 20;

    // Getter和Setter方法，用于读取和设置属性值
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}