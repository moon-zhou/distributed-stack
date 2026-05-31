# Contract Driven Enhancement Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 让当前前后端分离项目真正具备可重复执行的 OpenSpec 前端生成流程，减少手写接口漂移。

**Architecture:** 在后端 OpenAPI 中补齐稳定 `operationId`，在前端引入 `openapi-typescript-codegen` 生成客户端，并通过现有 service 层做薄封装，以保持页面调用面稳定。文档同步更新契约协作流程。

**Tech Stack:** OpenAPI 3.0.3, Spring Boot, Vue 3, Vite, openapi-typescript-codegen

---

### Task 1: 规范 OpenAPI 命名

**Files:**
- Modify: `backend/src/main/resources/openapi/openapi.yaml`

**Step 1: 为每个接口添加 operationId**

补齐稳定命名，避免自动生成方法名和构建警告。

**Step 2: 保持现有请求/响应结构不变**

确保只是规范命名，不引入接口语义变化。

### Task 2: 引入前端生成工具

**Files:**
- Modify: `frontend/package.json`
- Create: `frontend/tsconfig.json`

**Step 1: 新增生成依赖**

引入 `openapi-typescript-codegen`。

**Step 2: 新增 generate 脚本**

通过 `npm run generate:api` 从后端 YAML 生成客户端。

### Task 3: 生成前端客户端

**Files:**
- Create: `frontend/src/generated/**`

**Step 1: 执行生成命令**

从 `../backend/src/main/resources/openapi/openapi.yaml` 生成客户端代码。

**Step 2: 保持生成目录可被前端直接导入**

生成 axios 客户端，避免再手写底层请求结构。

### Task 4: 替换手写服务层

**Files:**
- Modify: `frontend/src/services/auth.js`
- Modify: `frontend/src/services/users.js`
- Modify: `frontend/src/services/orders.js`
- Create: `frontend/src/services/generatedClient.js`

**Step 1: 配置生成客户端 base/token**

让生成客户端走当前前端代理和 JWT 机制。

**Step 2: 用生成客户端替换底层调用**

保留对页面层的函数签名，减少上层改动。

### Task 5: 更新文档

**Files:**
- Modify: `README.md`
- Modify: `frontend/README.md`
- Modify: `OPENSPEC_搭建技术文档.md`

**Step 1: 写明生成命令**

补充 `npm run generate:api` 的使用时机。

**Step 2: 写明协作顺序**

明确“先改 spec，再生成客户端”的团队流程。

### Task 6: 验证

**Files:**
- Verify: `backend/`
- Verify: `frontend/`

**Step 1: 验证后端构建**

Run: `cd backend && mvn test`

**Step 2: 验证前端生成与构建**

Run: `cd frontend && npm run generate:api && npm run build`

**Step 3: 记录残余风险**

若无类型检查，则在结果里明确说明边界。