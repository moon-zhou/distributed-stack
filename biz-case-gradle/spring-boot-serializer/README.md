# Spring Boot Serializer 示例

这是一个基于 Spring Boot 3 和 GraalVM 24 的示例项目，演示了如何自定义序列化和反序列化方法。

## 项目介绍

本项目演示了如何在 Spring Boot 应用中实现自定义的序列化和反序列化功能：

1. 入参中特殊注解标注的字段值，获取之后，通过固定算法转换成另外的值进行替换
2. 出参里特殊注解标注的字段值，除了原样输出之外，还按照注解里的 value 作为字段值，再进行一次输出

## 环境准备

1. JDK 24
2. GraalVM (配置 GRAALVM_24_HOME 环境变量)
3. Gradle 8.x

## 功能说明

### 自定义注解

项目中定义了 `@EncryptField` 注解，用于标记需要特殊处理的字段：

```java
@EncryptField(algorithm = "BASE64", suffix = "Encrypted")
private String phone;
```

参数说明：
- `algorithm`: 加密算法类型，支持 BASE64 和 SIMPLE
- `suffix`: 序列化时额外输出的字段名后缀

### 序列化处理

对于带有 `@EncryptField` 注解的字段，在序列化输出时会：
1. 原样输出字段值
2. 根据算法加密后，以 `原字段名 + suffix` 的形式额外输出一次

例如：
```json
{
  "phone": "13812345678",
  "phone_encrypted": "MTM4MTIzNDU2Nzg="
}
```

### 反序列化处理

对于带有 `@EncryptField` 注解的字段，在反序列化时会自动解密：
1. 接收到加密的字段值
2. 根据注解指定的算法进行解密
3. 将解密后的值赋给对应字段

## 构建和运行

### 1. 运行在JVM模式下

```bash
./gradlew bootRun
```

### 2. 构建原生镜像

```bash
./gradlew nativeCompile
```

### 3. 运行原生镜像

```bash
./build/native/nativeCompile/spring-boot-serializer
```

### 4. 构建Docker镜像

```bash
./gradlew bootBuildImage
```

然后运行:
```bash
docker run --rm -p 8080:8080 spring-boot-serializer:0.0.1-SNAPSHOT
```

## 接口测试

项目启动后，可以通过以下接口进行测试：

### 获取用户信息

```
GET http://localhost:8080/user
```

### 创建用户

```
POST http://localhost:8080/user

{
  "id": 2,
  "name": "李四",
  "phone": "MTM4ODc2NTQzMjE=",  // BASE64加密的"13887654321"
  "email": "kibhboqmf!fybnqmf/dpn",  // SIMPLE加密的"lchang@sample.com"
  "age": 30,
  "active": true
}
```

### 更新用户

```
PUT http://localhost:8080/user

{
  "id": 1,
  "name": "张三丰",
  "phone": "MTM4MTIzNDU2Nzg=",  // BASE64加密的"13812345678"
  "email": "zhangsan!fybnqmf/dpn",  // SIMPLE加密的"zhangsan@example.com"
  "age": 26,
  "active": false
}
```