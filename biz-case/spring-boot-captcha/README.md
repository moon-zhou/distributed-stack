# Spring Boot 验证码示例项目

本项目演示了在Spring Boot 3中使用三种不同的验证码组件：

1. Hutool-captcha
2. easy-captcha
3. tianai-captcha

## 项目特点

- 基于Spring Boot 3.2.4
- 使用JDK 21
- 集成Hutool、EasyCaptcha和TianaiCaptcha三种验证码组件
- 提供RESTful API接口
- 支持Session和Redis两种存储方式
- 支持设备ID识别
- 支持指定存储类型进行测试
- 采用面向接口编程，易于扩展
- 统一参数封装，便于维护

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
        │               │   ├── CaptchaConfig.java
        │               │   └── RedisConfig.java
        │               ├── controller
        │               │   └── CaptchaController.java
        │               ├── dto
        │               │   └── CaptchaRequestDTO.java
        │               └── service
        │                   ├── CaptchaGenerator.java
        │                   ├── CaptchaService.java
        │                   ├── CaptchaStorageService.java
        │                   └── impl
        │                       ├── CaptchaServiceImpl.java
        │                       ├── HutoolCaptchaGenerator.java
        │                       ├── EasyCaptchaGenerator.java
        │                       ├── TianaiCaptchaGenerator.java
        │                       ├── SessionCaptchaStorageServiceImpl.java
        │                       └── RedisCaptchaStorageServiceImpl.java
        └── resources
            └── application.yml
```

## 依赖组件

1. **Hutool-captcha**: Hutool工具库中的验证码模块
2. **easy-captcha**: 简单易用的Java图形验证码
3. **tianai-captcha**: 行为验证码，支持滑块、旋转、点选等多种验证方式
4. **Spring Data Redis**: 用于Redis存储支持

## 接口说明

### Hutool Captcha
- 生成验证码(默认存储): `GET /captcha/hutool?deviceID=xxx`
- 验证验证码(默认存储): `GET /captcha/hutool/validate?code=xxxx&deviceID=xxx`
- 生成验证码(Session存储): `GET /captcha/hutool/session?deviceID=xxx`
- 验证验证码(Session存储): `GET /captcha/hutool/validate/session?code=xxxx&deviceID=xxx`
- 生成验证码(Redis存储): `GET /captcha/hutool/redis?deviceID=xxx`
- 验证验证码(Redis存储): `GET /captcha/hutool/validate/redis?code=xxxx&deviceID=xxx`

### Easy Captcha
- 生成验证码(默认存储): `GET /captcha/easy?deviceID=xxx`
- 验证验证码(默认存储): `GET /captcha/easy/validate?code=xxxx&deviceID=xxx`
- 生成验证码(Session存储): `GET /captcha/easy/session?deviceID=xxx`
- 验证验证码(Session存储): `GET /captcha/easy/validate/session?code=xxxx&deviceID=xxx`
- 生成验证码(Redis存储): `GET /captcha/easy/redis?deviceID=xxx`
- 验证验证码(Redis存储): `GET /captcha/easy/validate/redis?code=xxxx&deviceID=xxx`

### Tianai Captcha
- 生成验证码(默认存储): `GET /captcha/tianai?deviceID=xxx`
- 验证验证码(默认存储): `POST /captcha/tianai/validate` (需要JSON格式请求体)
- 生成验证码(Session存储): `GET /captcha/tianai/session?deviceID=xxx`
- 验证验证码(Session存储): `POST /captcha/tianai/validate/session` (需要JSON格式请求体)
- 生成验证码(Redis存储): `GET /captcha/tianai/redis?deviceID=xxx`
- 验证验证码(Redis存储): `POST /captcha/tianai/validate/redis` (需要JSON格式请求体)

## 配置说明

在 `application.yml` 中可以通过以下配置项控制验证码存储方式：

```yaml
captcha:
  storage: session # 可选值: session (默认) 或 redis
```

## 架构说明

### 验证码生成器 (CaptchaGenerator)
项目通过[CaptchaGenerator](src/main/java/org/moonzhou/captcha/service/CaptchaGenerator.java)接口抽象了不同验证码组件的生成逻辑，目前实现了：
- [HutoolCaptchaGenerator](src/main/java/org/moonzhou/captcha/service/impl/HutoolCaptchaGenerator.java)
- [EasyCaptchaGenerator](src/main/java/org/moonzhou/captcha/service/impl/EasyCaptchaGenerator.java)
- [TianaiCaptchaGenerator](src/main/java/org/moonzhou/captcha/service/impl/TianaiCaptchaGenerator.java)

### 存储服务 (CaptchaStorageService)
通过[CaptchaStorageService](src/main/java/org/moonzhou/captcha/service/CaptchaStorageService.java)接口抽象了验证码的存储逻辑，支持多种存储方式：
- [SessionCaptchaStorageServiceImpl](src/main/java/org/moonzhou/captcha/service/impl/SessionCaptchaStorageServiceImpl.java) - 基于Session存储
- [RedisCaptchaStorageServiceImpl](src/main/java/org/moonzhou/captcha/service/impl/RedisCaptchaStorageServiceImpl.java) - 基于Redis存储

### 参数封装 (CaptchaRequestDTO)
通过[CaptchaRequestDTO](src/main/java/org/moonzhou/captcha/dto/CaptchaRequestDTO.java)统一封装所有验证码相关的请求参数，提高代码可维护性和一致性。

这种设计符合面向接口编程的原则，易于扩展新的验证码生成器和存储方式。

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

1. 验证码在验证一次后会从存储中清除
2. 验证码不区分大小写
3. 如果使用Redis存储，需要配置Redis连接参数
4. Tianai验证码验证接口使用POST方法，并需要JSON格式的请求体，包含code和deviceID字段
