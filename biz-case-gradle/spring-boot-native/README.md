# Spring Boot Native 示例

这是一个基于Spring Boot 3和GraalVM的原生镜像示例项目。

## 项目介绍

本项目演示了如何使用Spring Boot 3和GraalVM构建原生镜像应用。原生镜像相比传统的JVM运行方式具有启动快、内存占用少等优势。

## 环境准备

1. JDK 24
2. GraalVM (配置 GRAALVM_24_HOME 环境变量)
3. Gradle 8.x

### GraalVM 24 配置

确保已安装 GraalVM 24，并设置环境变量：

```bash
export GRAALVM_24_HOME=～/local/graalvm-jdk-24.0.1+9.1/Contents/Home
```

或者在 `gradle.properties` 文件中指定路径：

```properties
org.gradle.java.installations.paths=～/local/graalvm-jdk-24.0.1+9.1/Contents/Home
org.gradle.java.installations.auto-detect=true
org.gradle.java.installations.auto-download=false
org.gradle.java.installations.fromEnv=GRAALVM_24_HOME
# 启用工具链自动配置
org.gradle.java.installations.auto-provisioning=true
```

注意：Gradle 8.10.2及以上版本对Java 24有更好的支持。本项目使用Gradle 8.14.3。

## 构建和运行

### 1. 运行在JVM模式下

```bash
./gradlew bootRun
```

访问示例端点:
- http://localhost:8080/hello
- http://localhost:8080/hello-with-name?name=YourName
- http://localhost:8080/info

### 2. 构建原生镜像

```bash
./gradlew nativeCompile
```

### 3. 运行原生镜像

```bash
./build/native/nativeCompile/spring-boot-native
```

### 4. 构建Docker镜像

```bash
./gradlew bootBuildImage
```

然后运行:
```bash
docker run --rm -p 8080:8080 spring-boot-native:0.0.1-SNAPSHOT
```

## 端点说明

- `GET /hello`: 返回简单的问候语
- `GET /hello-with-name?name=xxx`: 返回带名称的问候语
- `GET /info`: 返回应用信息和当前时间

## 性能优势

原生镜像相比传统JAR包方式具有以下优势:
- 启动时间更快（通常在100ms以内）
- 内存占用更少（通常减少60-70%）
- 更小的部署包体积
- 更快的响应时间

## 常见问题解决

### Java版本兼容性问题

如果遇到以下错误：
```
Cannot find a Java installation on your machine matching: {languageVersion=24, vendor=GraalVM Community, implementation=vendor-specific}
```

请确保：

1. 使用Gradle 8.10.2或更高版本（本项目已配置为8.14.3）
2. 在gradle.properties中正确配置Java安装路径
3. 确保GraalVM 24已正确安装且路径正确

### 构建性能优化

构建原生镜像可能需要较长时间（通常2-3分钟），这是正常现象。可以考虑以下优化：

1. 使用Profile-Guided Optimizations (PGO)提高运行时性能
2. 在开发阶段使用JVM模式运行，仅在生产部署时构建原生镜像
3. 根据需要设置最大堆大小以获得更可预测的内存使用情况