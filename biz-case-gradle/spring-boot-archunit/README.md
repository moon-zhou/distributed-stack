# Spring Boot ArchUnit 示例

这是一个基于 Spring Boot 和 ArchUnit 的示例项目，演示了如何使用 ArchUnit 来验证和保证项目的架构规范。

## 项目介绍

本项目演示了如何在 Spring Boot 项目中集成和使用 ArchUnit 进行架构测试。ArchUnit 是一个免费、简单且可扩展的库，用于检查 Java 代码的架构。它可以检查包和类之间的依赖关系、层和切片、循环依赖等。

## 环境准备

1. JDK 24
2. Gradle 8.x

## 项目结构

```
src
├── main
│   └── java
│       └── org
│           └── moonzhou
│               └── springbootarchunit
│                   ├── controller
│                   ├── service
│                   ├── repository
│                   ├── model
│                   └── SpringBootArchUnitApplication.java
└── test
    └── java
        └── org
            └── moonzhou
                └── springbootarchunit
                    └── architecture
```

## ArchUnit 测试示例

项目中包含了多种类型的 ArchUnit 测试：

### 1. 分层架构测试 (LayeredArchitectureTest.java)

验证应用程序的分层架构：
- Controller 层只能被外部访问
- Service 层只能被 Controller 层访问
- Repository 层只能被 Service 层访问
- Model 层可以被所有层访问

### 2. 命名规范测试 (NamingConventionTest.java)

验证类的命名规范：
- Service 类必须以 "Service" 结尾
- Repository 类必须以 "Repository" 结尾
- Controller 类必须以 "Controller" 结尾

### 3. 注解使用测试 (AnnotationTest.java)

验证类的注解使用：
- Service 类必须使用 @Service 注解
- Repository 类必须使用 @Repository 注解
- Controller 类必须使用 @RestController 或 @Controller 注解

### 4. 通用编码规则测试 (CodingRulesTest.java)

验证通用编码规则：
- 禁止使用字段注入
- 禁止使用 java.util.logging
- 禁止使用 JodaTime
- 禁止访问标准流 (System.out, System.err)
- 禁止抛出通用异常

## 构建和运行

### 1. 构建项目

```bash
./gradlew build
```

### 2. 运行测试（包括 ArchUnit 架构测试）

```bash
./gradlew test
```

### 3. 运行应用程序

```bash
./gradlew bootRun
```

## API 端点

- `GET /users` - 获取所有用户
- `GET /users/{id}` - 根据 ID 获取用户
- `POST /users` - 创建新用户
- `DELETE /users/{id}` - 根据 ID 删除用户

## ArchUnit 优势

使用 ArchUnit 可以带来以下优势：

1. **自动化架构验证** - 通过单元测试自动验证架构规则，无需手动代码审查
2. **早期发现问题** - 在开发阶段就能发现架构违规问题
3. **保持架构一致性** - 确保团队成员遵循统一的架构规范
4. **可维护性** - 架构规则以代码形式存在，易于维护和更新
5. **可读性强** - ArchUnit 的规则语法接近自然语言，易于理解和编写

## 自定义 ArchUnit 规则

你可以根据项目需要自定义更多 ArchUnit 规则，例如：

1. 包依赖规则
2. 循环依赖检查
3. 特定注解的使用规则
4. 类成员访问规则
5. 继承关系规则

更多详细信息请参考 [ArchUnit 官方文档](https://www.archunit.org/)