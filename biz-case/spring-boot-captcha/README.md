# Spring Boot 验证码示例项目

本项目演示了在Spring Boot 3中使用两种不同的验证码组件：

1. Hutool-captcha
2. easy-captcha

## 项目特点

- 基于Spring Boot 3.2.4
- 使用JDK 21
- 集成Hutool和EasyCaptcha两种验证码组件
- 提供RESTful API接口

## 项目结构

```
├── pom.xml
├── README.md
└── src
    └── main
        ├── java
        │   └── org
        │       └── moonzhou
        │           └── captcha
        │               ├── CaptchaApplication.java
        │               ├── config
        │               │   └── CaptchaConfig.java
        │               ├── controller
        │               │   └── CaptchaController.java
        │               └── service
        │                   ├── CaptchaService.java
        │                   └── impl
        │                       └── CaptchaServiceImpl.java
        └── resources
            └── application.yml
```

## 依赖组件

1. **Hutool-captcha**: Hutool工具库中的验证码模块
2. **easy-captcha**: 简单易用的Java图形验证码

## 接口说明

### Hutool Captcha
- 生成验证码: `GET /captcha/hutool`
- 验证验证码: `GET /captcha/hutool/validate?code=xxxx`

### Easy Captcha
- 生成验证码: `GET /captcha/easy`
- 验证验证码: `GET /captcha/easy/validate?code=xxxx`

## 运行项目

```bash
cd spring-boot-captcha
mvn spring-boot:run
```

或者打包运行：

```bash
mvn clean package
java -jar target/spring-boot-captcha-1.0-SNAPSHOT.jar
```

## 使用说明

1. 访问对应接口生成验证码图片
2. 输入验证码进行验证
3. 验证成功返回true，失败返回false

## 注意事项

1. 验证码在验证一次后会从session中清除
2. 验证码不区分大小写