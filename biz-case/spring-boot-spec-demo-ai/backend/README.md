# Backend README

当前目录是拆分后的 Spring Boot 后端工程，负责：

- JWT 登录认证
- 用户 CRUD
- 订单创建与查询
- OpenAPI-First 契约驱动开发
- Swagger UI 与 OpenSpec 文档展示

## 核心路径

```text
src/main/resources/openapi/openapi.yaml   OpenAPI 契约源
src/main/java/.../controller/             控制器，实现生成接口
src/main/java/.../service/                业务逻辑
src/main/resources/db/migration/          Flyway 迁移脚本
```

## 运行方式

```bash
mvn spring-boot:run
```

启动后访问：

- Swagger UI：`http://localhost:8080/swagger-ui.html`
- H2 Console：`http://localhost:8080/h2-console`

## 演示账号

- 用户名：`admin`
- 密码：`password`

## 常用验证

### 登录

```bash
curl -s -X POST http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"admin","password":"password"}'
```

### 查询用户

```bash
TOKEN='<paste-token>'
curl -s http://localhost:8080/users -H "Authorization: Bearer $TOKEN"
```

### 创建订单

```bash
curl -s -X POST http://localhost:8080/orders \
  -H 'Content-Type: application/json' \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"userId":1,"items":[{"productId":1,"quantity":2},{"productId":2,"quantity":1}]}'
```

## OpenAPI-First 工作流

1. 修改 `src/main/resources/openapi/openapi.yaml`
2. 执行 `mvn generate-sources`
3. 调整 Controller/Service 实现
4. 执行测试或启动验证

推荐命令：

```bash
mvn clean test
```

## 与前端联调

- 默认允许 `http://localhost:5173` 进行跨域访问
- 前端通过代理调用 `/auth`、`/users`、`/orders`
- JWT 仍然是唯一认证方式
