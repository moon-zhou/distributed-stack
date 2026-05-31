# OpenSpec 搭建技术文档（前后端分离版）

## 1. 文档目标

本文档结合当前仓库的拆分结果，总结如何基于 OpenSpec（OpenAPI-First）搭建一个前后端分离项目，并说明当前仓库中的具体落地方式。

- 后端目录：`backend/`
- 前端目录：`frontend/`
- 契约源：`backend/src/main/resources/openapi/openapi.yaml`

## 2. 当前项目架构

当前仓库采用单仓双应用结构：

```text
backend/   Spring Boot API 服务
frontend/  Vue 3 Web 客户端
```

职责划分如下：

- OpenSpec 由后端维护，作为唯一接口契约源
- 后端根据契约生成接口与模型，并实现业务逻辑
- 前端按契约消费接口，进行登录、用户和订单页面渲染

这种结构的核心价值是：

- 接口变更入口唯一
- 前后端字段语义一致
- 文档、代码、调试路径统一

## 3. OpenSpec 在本项目中的位置

契约文件：

```text
backend/src/main/resources/openapi/openapi.yaml
```

当前定义的核心接口包括：

- `/auth/login`
- `/users`
- `/users/{id}`
- `/orders`
- `/orders/{id}`

当前定义的核心模型包括：

- `LoginRequest`
- `LoginResponse`
- `UserCreateRequest`
- `UserUpdateRequest`
- `UserResponse`
- `CreateOrderRequest`
- `OrderResponse`

## 4. 后端搭建过程

### 4.1 引入基础依赖

后端 `backend/pom.xml` 中引入：

- `spring-boot-starter-web`
- `spring-boot-starter-security`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `flyway-core`
- `h2`
- `jjwt-*`
- `springdoc-openapi-starter-webmvc-ui`
- `openapi-generator-maven-plugin`

### 4.2 配置 OpenAPI Generator

当前项目通过 `openapi-generator-maven-plugin` 在 `generate-sources` 阶段生成 Spring 接口和模型。

关键配置：

- `generatorName=spring`
- `interfaceOnly=true`
- `useSpringBoot3=true`
- `skipDefaultInterface=true`
- `useTags=true`

这意味着：

- 只生成接口和模型
- Controller 负责实现生成接口
- 契约变更会直接体现在编译期签名上

### 4.3 实现生成接口

当前后端 Controller 对应关系：

- `AuthController implements AuthApi`
- `UserController implements UsersApi`
- `OrderController implements OrdersApi`

实现流程：

1. 接收生成模型
2. 转换为内部 DTO
3. 调用 Service
4. 输出生成模型响应

### 4.4 配置安全体系

后端通过以下组件对齐契约中的 `bearerAuth`：

- `SecurityConfig`
- `JwtAuthenticationFilter`
- `JwtService`

同时，为了支持前后端分离联调，额外增加：

- `WebConfig`：允许 `http://localhost:5173` 跨域访问
- `SecurityConfig.cors()`：启用 Spring Security CORS 支持

### 4.5 数据与迁移

Flyway 迁移脚本位于：

```text
backend/src/main/resources/db/migration/
```

当前初始化内容包括：

- `users`
- `products`
- `orders`
- `order_items`

以及演示数据：

- 管理员账号 `admin/password`
- 商品 `Keyboard`、`Mouse`、`Monitor`

## 5. 前端接入过程

### 5.1 创建 Vue 3 工程

前端基于 Vue 3 + Vite 构建，核心文件：

```text
frontend/package.json
frontend/vite.config.js
frontend/src/router/index.js
frontend/src/services/
frontend/src/views/
```

### 5.2 配置开发代理

为了让前端开发环境直接调用后端，Vite 代理配置了：

- `/auth`
- `/users`
- `/orders`

它们都会转发到：

```text
http://localhost:8080
```

### 5.3 封装 API 调用层

前端现在采用“生成客户端 + 薄适配层”模式：

- 通过 `npm run generate:api` 从 OpenAPI 生成 `src/generated/`
- 通过 `src/services/generatedClient.js` 统一配置 `OpenAPI.BASE` 与 `OpenAPI.TOKEN`
- 通过 `src/services/auth.js`、`users.js`、`orders.js` 对页面层暴露稳定函数

这样做的好处是：

- 底层请求结构不再手写
- 接口名直接来源于 `operationId`
- 页面层改动面最小

### 5.4 页面消费契约

当前页面：

- 登录页：消费 `LoginRequest/LoginResponse`
- 用户页：消费 `UserCreateRequest`、`UserUpdateRequest`、`UserResponse`
- 订单页：消费 `CreateOrderRequest`、`OrderResponse`

这说明 OpenSpec 不只是后端生成工具，也直接约束前端字段结构和提交流程。

## 6. 推荐迭代流程

当前仓库推荐的 OpenSpec 迭代顺序如下：

1. 修改 `backend/src/main/resources/openapi/openapi.yaml`
2. 在后端执行 `mvn generate-sources`
3. 修复后端 Controller/Service 编译与实现
4. 在前端执行 `npm run generate:api`
5. 若页面字段变化，再调整前端薄适配层或页面
6. 分别验证后端测试和前端构建

建议命令：

```bash
cd backend && mvn clean test
cd frontend && npm run generate:api && npm run build
```

## 7. 运行链路

### 7.1 启动后端

```bash
cd backend
mvn spring-boot:run
```

### 7.2 启动前端

```bash
cd frontend
npm install
npm run dev
```

### 7.3 登录与调用

前端登录后会获取 JWT，并用于调用：

- `/users`
- `/orders`

也可以继续用 curl 验证：

```bash
curl -s -X POST http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"admin","password":"password"}'
```

## 8. 常见问题

### 8.1 后端改了 spec，但前端页面没同步

这是前后端分离项目最常见的问题。正确顺序不是只改后端，而是：

1. 改 spec
2. 改后端生成与实现
3. 执行前端客户端生成
4. 再改前端页面字段

### 8.2 为什么要补 operationId

如果缺少 `operationId`：

- 后端生成器会自动命名并输出警告
- 前端生成客户端的方法名不稳定
- 后续接口调整更容易产生无意义变更

因此，`operationId` 应该被视为 OpenSpec 的必填项。

### 8.3 前端本地调用报跨域错误

检查：

- 后端 `WebConfig` 是否存在
- `SecurityConfig` 是否启用了 `cors()`
- 前端是否通过 Vite 代理访问

### 8.4 Swagger 无法展示契约

检查：

- 后端 `springdoc.swagger-ui.url` 是否仍指向 `/openapi/openapi.yaml`
- 安全配置是否放行 Swagger 路径

## 9. 总结

当前项目已经从单体后端示例演进为前后端分离的 OpenSpec 示例仓库，形成了完整闭环：

- OpenSpec 统一定义接口契约
- 后端根据契约生成接口并实现业务
- 前端根据契约进行页面和请求封装
- 文档、调试和联调路径都围绕同一份契约展开

这套模式适合作为团队 API-First + 前后端分离的入门模板继续扩展。
