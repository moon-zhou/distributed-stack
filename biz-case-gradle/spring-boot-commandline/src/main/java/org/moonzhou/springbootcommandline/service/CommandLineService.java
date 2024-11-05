package org.moonzhou.springbootcommandline.service;

public class CommandLineService {
    private final String name;
    private final int age;

    // 通过构造函数注入配置属性
    public CommandLineService(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 提供一个方法，用于打印欢迎信息
    public String welcome() {
        return "Welcome to command line, " + name + "! You are " + age + " years old.";
    }
}