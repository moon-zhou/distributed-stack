# 契约驱动增强设计

## 背景

当前仓库已经完成前后端分离，但前端接口层仍以手写 Axios 服务为主，后端 OpenAPI 文档也缺少显式 `operationId`，导致：

- OpenAPI Generator 每次生成仍有命名警告
- 前端和 OpenSpec 的绑定关系不够直接
- 后续接口变更仍然依赖手工同步前端服务层

## 目标

- 为 OpenAPI 补齐稳定的 `operationId`
- 为前端引入可重复执行的 OpenAPI 客户端生成流程
- 保持当前页面层 API 不变，降低业务代码改动范围
- 将契约迭代流程文档化，形成“改 spec -> 生成 -> 实现 -> 验证”闭环

## 方案选择

采用轻量增强方案：

1. 在 `backend/src/main/resources/openapi/openapi.yaml` 中补 `operationId`
2. 在 `frontend/` 中引入 `openapi-typescript-codegen`
3. 生成 `src/generated/` 客户端代码
4. 保留现有 `src/services/*.js` 作为最薄适配层，对页面层隐藏生成代码细节

不在本次改造中：

- 全量迁移前端到 TypeScript
- 引入更重的 API 生成工作流（如 Orval、OpenAPI Generator 前端模板）
- 重写所有页面组件

## 设计细节

### 契约层

为每个接口增加稳定命名的 `operationId`，例如：

- `login`
- `createUser`
- `listUsers`
- `getUserById`
- `updateUser`
- `deleteUser`
- `createOrder`
- `listOrders`
- `getOrderById`

这样可以：

- 消除生成器警告
- 为前端生成客户端提供稳定函数名

### 前端生成层

在 `frontend/package.json` 中新增：

- 生成依赖
- `generate:api` 脚本

生成源为：

```text
../backend/src/main/resources/openapi/openapi.yaml
```

输出目录为：

```text
src/generated/
```

### 前端适配层

保留现有：

- `src/services/auth.js`
- `src/services/users.js`
- `src/services/orders.js`

但将其底层调用改为生成客户端，页面仍使用现有函数接口，避免大面积改动。

### 文档层

更新：

- 根 README
- 前端 README
- OpenSpec 技术文档

明确说明：

- 何时执行 `npm run generate:api`
- `operationId` 命名的重要性
- 前后端协作的推荐顺序

## 验证方式

- 后端：`cd backend && mvn test`
- 前端：`cd frontend && npm run generate:api && npm run build`

## 风险与取舍

- 生成代码会引入一批新文件，但能换来可重复同步能力
- 不启用前端类型检查，只保证生成与构建链路可运行
- 若未来前端全面转 TS，可直接复用这套生成目录