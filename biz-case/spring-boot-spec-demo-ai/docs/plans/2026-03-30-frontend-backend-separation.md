# Frontend Backend Separation Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 将当前 OpenSpec Spring Boot 示例改造为前后端分离仓库，新增 Vue 3 前端并拆分文档。

**Architecture:** 现有 Spring Boot 项目整体迁移到 `backend/`，新增 `frontend/` 作为 Vue 3 + Vite 客户端。OpenSpec 契约保留在后端，前端通过 Axios 对接登录、用户和订单接口，并由根 README 统一说明协作方式。

**Tech Stack:** Spring Boot 3, OpenAPI Generator, Flyway, JWT, Vue 3, Vite, Vue Router, Axios

---

### Task 1: 迁移后端目录

**Files:**
- Create: `backend/`
- Move: `pom.xml`
- Move: `src/`
- Move: `README.md` -> `backend/README.md`

**Step 1: 创建 backend 目录**

创建 `backend/` 作为现有 Spring Boot 项目的新根目录。

**Step 2: 移动后端文件**

将 `pom.xml`、`src/` 和当前 README 全部迁入 `backend/`。

**Step 3: 确认路径变化后的构建根**

运行后端目录检查，确保 `backend/pom.xml` 是唯一构建入口。

### Task 2: 补充后端前端联调能力

**Files:**
- Modify: `backend/src/main/java/com/example/openspecdemo/config/SecurityConfig.java`
- Create: `backend/src/main/java/com/example/openspecdemo/config/WebConfig.java`

**Step 1: 添加 CORS 配置**

允许 `http://localhost:5173` 调用后端接口。

**Step 2: 保持鉴权路径不变**

确保 `/auth/login`、Swagger 相关路径仍可匿名访问。

**Step 3: 验证后端编译不受影响**

运行后端测试以确保改动未破坏现有功能。

### Task 3: 新建 Vue 3 前端

**Files:**
- Create: `frontend/package.json`
- Create: `frontend/vite.config.js`
- Create: `frontend/index.html`
- Create: `frontend/src/main.js`
- Create: `frontend/src/App.vue`
- Create: `frontend/src/router/index.js`
- Create: `frontend/src/style.css`

**Step 1: 初始化 Vite 基础结构**

添加 Vue 3 运行时、构建脚本和入口文件。

**Step 2: 配置路由与全局布局**

添加登录页、仪表盘、用户页、订单页的路由骨架。

**Step 3: 配置 Vite 代理**

将开发请求代理到 `http://localhost:8080`。

### Task 4: 实现前端 API 层与状态管理

**Files:**
- Create: `frontend/src/services/http.js`
- Create: `frontend/src/services/auth.js`
- Create: `frontend/src/services/users.js`
- Create: `frontend/src/services/orders.js`
- Create: `frontend/src/stores/session.js`

**Step 1: 封装 Axios 实例**

注入 token，统一错误处理。

**Step 2: 按业务拆分服务层**

分别封装登录、用户、订单 API。

**Step 3: 管理登录状态**

使用轻量模块管理 token 和当前登录状态。

### Task 5: 实现演示页面

**Files:**
- Create: `frontend/src/views/LoginView.vue`
- Create: `frontend/src/views/DashboardView.vue`
- Create: `frontend/src/views/UsersView.vue`
- Create: `frontend/src/views/OrdersView.vue`
- Create: `frontend/src/components/AppShell.vue`

**Step 1: 登录页对接 `/auth/login`**

成功后保存 token 并跳转。

**Step 2: 用户页实现 CRUD 交互**

支持列表、新增、编辑、删除。

**Step 3: 订单页实现列表和创建订单**

支持按现有 OpenSpec 字段提交订单项。

### Task 6: 重写文档

**Files:**
- Modify: `README.md`
- Modify: `OPENSPEC_搭建技术文档.md`
- Modify: `backend/README.md`
- Create: `frontend/README.md`

**Step 1: 重写根 README**

说明仓库结构、运行步骤、联调方式。

**Step 2: 更新后端 README**

聚焦 Spring Boot 与 OpenAPI-First 后端流程。

**Step 3: 更新 OpenSpec 文档**

补充前后端分离场景下的契约协作与迭代方式。

### Task 7: 验证

**Files:**
- Verify: `backend/`
- Verify: `frontend/`

**Step 1: 运行后端测试**

Run: `cd backend && mvn test`

**Step 2: 安装并构建前端**

Run: `cd frontend && npm install && npm run build`

**Step 3: 检查最终目录结构**

确认根目录只保留聚合文档和两个子应用。