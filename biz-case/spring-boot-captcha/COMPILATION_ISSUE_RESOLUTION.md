# Spring Boot Captcha 编译问题解决记录

## 问题描述

在编译 spring-boot-captcha 项目时可能会遇到编译错误，主要与 Servlet API 版本兼容性相关。

## 问题分析

1. 项目使用 Spring Boot 3.x 版本，该版本基于 Jakarta EE 9+，Servlet API 包名已从 `javax.servlet` 更改为 `jakarta.servlet`
2. 虽然 spring-boot-starter-web 间接包含了 Servlet API 依赖，但在某些环境下可能需要显式声明依赖以确保版本兼容性
3. 项目配置的 Java 版本为 21，需要确保所有依赖都与该版本兼容

## 解决方案

在项目的 pom.xml 文件中显式添加 Jakarta Servlet API 依赖：

```xml
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <scope>provided</scope>
</dependency>
```

该依赖的作用：
- 确保项目使用正确版本的 Servlet API
- 明确指定依赖关系，避免潜在的版本冲突
- 提高项目在不同环境下的可移植性

## 验证结果

通过运行以下命令验证修复效果：
```bash
mvn clean compile
mvn clean test
```

测试结果显示：
- 编译成功完成，没有错误
- 测试成功运行，所有测试用例都通过了
- 项目可以正常打包

## 最佳实践

1. 对于使用 Spring Boot 3.x 的项目，确保使用 `jakarta.servlet` 而不是 `javax.servlet`
2. 在多环境部署时，显式声明关键依赖可以提高项目的稳定性和可维护性
3. 定期检查依赖版本兼容性，特别是升级 Spring Boot 版本时