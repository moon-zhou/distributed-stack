# 前后端分离改造设计

## 背景

当前仓库是单一 Spring Boot OpenAPI-First 示例项目。目标是将其调整为前后端分离结构：现有 Spring Boot 后端迁移到独立 `backend` 目录，新增基于 Vue 3 的 `frontend` 应用，并保持前后端各自 README，同时更新根目录文档与 OpenSpec 技术文档。

## 设计目标

- 保留现有后端能力与 OpenAPI-First 实现方式
- 新增可运行的 Vue 3 演示前端，覆盖登录、用户管理、订单管理
- 仓库根目录仅承担聚合说明，不再作为后端项目根
- 前后端启动与联调路径清晰
- OpenSpec 文档从“后端生成代码”扩展为“前后端协作契约”视角

## 方案选择

采用单仓双应用结构：

- `backend/`：迁移现有 Spring Boot 项目
- `frontend/`：新建 Vue 3 + Vite 项目
- 根目录：聚合 README、OpenSpec 文档、设计与计划文档

不引入 monorepo 工具链，不增加容器编排或统一构建脚本，保持演示项目轻量可运行。

## 目录结构

```text
backend/
  pom.xml
  README.md
  src/
frontend/
  package.json
  README.md
  index.html
  vite.config.js
  src/
README.md
OPENSPEC_搭建技术文档.md
docs/plans/
```

## 后端设计

- 将现有 `pom.xml`、`src/`、原 README 迁移到 `backend/`
- 追加 CORS 配置，允许 Vue 本地开发访问
- 保持 JWT、安全配置、Flyway、OpenAPI Generator 机制不变
- 保持 Swagger/OpenAPI 文档可用

## 前端设计

- 使用 Vue 3 + Vite + Vue Router + Axios
- 页面：登录页、仪表盘、用户管理、订单管理
- 使用本地存储保存 token
- 通过 Axios 拦截器统一添加 `Authorization` 头
- 通过 Vite dev server 代理 `/api` 到后端，并在前端服务层把 `/auth`、`/users`、`/orders` 统一映射到后端

## API 协作设计

- OpenSpec 仍以 `backend/src/main/resources/openapi/openapi.yaml` 为单一契约源
- 前端按契约字段设计表单和展示结构
- 根目录文档明确“先改 spec，再生成后端，再同步前端接口层”流程

## 文档设计

- 根 README：说明仓库结构、启动方式、联调方式
- `backend/README.md`：说明后端运行、OpenAPI-First 工作流、Swagger、测试方式
- `frontend/README.md`：说明前端启动、路由、API 代理与演示功能
- `OPENSPEC_搭建技术文档.md`：更新为前后端分离架构下的 OpenSpec 实践文档

## 验证方式

- 后端：`mvn test`
- 前端：`npm install` 后 `npm run build`
- 结构：确认根目录只保留聚合文档与子应用目录

## 风险与取舍

- 不在本次改造中引入 TypeScript，降低脚手架复杂度
- 不在本次改造中自动生成前端类型，避免超出演示项目范围
- 不做后端接口语义变更，避免影响现有 OpenSpec 链路