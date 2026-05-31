# Spring Boot OpenSpec Demo AI

当前仓库已调整为前后端分离结构：后端使用 Spring Boot + OpenAPI-First，前端使用 Vue 3 + Vite。

## 目录结构

```text
backend/    Spring Boot 后端，负责 OpenSpec 契约、JWT、安全与数据持久化
frontend/   Vue 3 前端，负责登录、用户管理、订单管理演示页面
docs/plans/ 设计文档与实施计划
OPENSPEC_搭建技术文档.md
```

## 技术栈

- 后端：Spring Boot 3、Spring Security、JPA、Flyway、OpenAPI Generator、H2
- 前端：Vue 3、Vue Router、Axios、Vite
- 契约：OpenAPI 3.0.3

## 启动方式

### 1. 启动后端

```bash
cd backend
mvn spring-boot:run
```

后端默认地址：`http://localhost:8080`

### 2. 启动前端

```bash
cd frontend
npm install
npm test
npm run dev
```

前端默认地址：`http://localhost:5173`

## 联调说明

- 前端通过 Vite 代理调用后端接口
- 当前代理路径：`/auth`、`/users`、`/orders`
- 登录成功后，前端会将 JWT token 写入本地存储，并在后续请求中自动带上 `Authorization: Bearer <token>`
- 前端 API 客户端由 OpenAPI 自动生成，生成命令位于 `frontend/package.json`

## 演示账号

- 用户名：`admin`
- 密码：`password`

## 页面功能

- 登录页：调用 `/auth/login`
- 概览页：展示用户和订单数量
- 用户管理：列表、新增、编辑邮箱、删除
- 订单管理：列表、基于预置商品创建订单

## 前端测试

前端已补充最小测试体系，覆盖：

- 生成客户端配置
- 登录页成功与失败流程
- 用户页加载、创建、删除流程
- 订单页加载与提交流程

执行命令：

```bash
cd frontend
npm test
```

## OpenSpec 协作入口

统一契约文件位于：

```text
backend/src/main/resources/openapi/openapi.yaml
```

推荐协作流程：

1. 先修改 OpenAPI 契约
2. 在后端执行 `mvn generate-sources`
3. 调整后端实现
4. 在前端执行 `npm run generate:api`
5. 同步页面字段或薄适配层
6. 回归验证前后端联调

前端生成命令：

```bash
cd frontend
npm run generate:api
```

详细说明见根目录 `OPENSPEC_搭建技术文档.md`。