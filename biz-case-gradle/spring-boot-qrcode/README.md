# Spring Boot QRCode Generator

一个基于Spring Boot和ZXing库的二维码生成服务，支持Native Image编译。

## 功能特性

- 生成二维码并返回Base64编码字符串
- 直接返回二维码图片
- 可自定义二维码尺寸
- 支持Spring Boot Native Image编译

## API接口

### 1. 生成二维码(Base64格式)

```
GET /qrcode/generate?content={内容}&width={宽度}&height={高度}
```

参数说明:
- content: 二维码内容（必需）
- width: 二维码宽度，默认300（可选）
- height: 二维码高度，默认300（可选）

返回示例:
```json
{
  "qrCode": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAAEsCAIAAAD2...",
  "message": "二维码生成成功"
}
```

### 2. 生成二维码(图片格式)

```
GET /qrcode/image?content={内容}&width={宽度}&height={高度}
```

参数说明:
- content: 二维码内容（必需）
- width: 二维码宽度，默认300（可选）
- height: 二维码高度，默认300（可选）

返回: PNG格式的二维码图片

## 构建和运行

### JVM模式运行

```bash
./gradlew bootRun
```

### 构建Native Image

```bash
./gradlew nativeCompile
```

运行生成的Native Image:
```bash
./build/native/nativeCompile/spring-boot-qrcode
```

### 构建Docker镜像

```bash
./gradlew bootBuildImage
```

## 使用示例

1. 生成默认尺寸的二维码:
   ```
   GET http://localhost:8080/qrcode/generate?content=Hello World
   ```

2. 生成自定义尺寸的二维码:
   ```
   GET http://localhost:8080/qrcode/generate?content=Hello World&width=500&height=500
   ```

3. 直接获取二维码图片:
   ```
   GET http://localhost:8080/qrcode/image?content=Hello World
   ```