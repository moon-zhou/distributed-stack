# Frontend README

当前目录是拆分后的 Vue 3 前端工程，用于演示如何在前后端分离架构中消费当前后端的 OpenSpec 接口。

## 技术栈

- Vue 3
- Vue Router
- Axios
- Vite

## 启动方式

```bash
npm install
npm test
npm run dev
```

默认访问地址：`http://localhost:5173`

## 测试

当前前端已接入最小测试体系，执行命令：

```bash
npm test
```

当前覆盖范围：

- `src/services/generatedClient.test.js`
- `src/views/LoginView.test.js`
- `src/views/UsersView.test.js`
- `src/views/OrdersView.test.js`

## 页面说明

- `/login`：登录页，对接 `/auth/login`
- `/`：概览页，展示用户数与订单数
- `/users`：用户列表与用户创建/编辑
- `/orders`：订单列表与订单创建

## API 代理

Vite 已配置代理到 `http://localhost:8080`：

- `/auth`
- `/users`
- `/orders`

这意味着前端开发模式下无需额外处理基础地址。

## OpenAPI 客户端生成

当前前端不再手写底层请求结构，而是通过 OpenSpec 自动生成客户端。

生成命令：

```bash
npm run generate:api
```

生成来源：

```text
../backend/src/main/resources/openapi/openapi.yaml
```

生成目录：

```text
src/generated/
```

页面层仍通过 `src/services/*.js` 调用接口，但这些 service 现在只是对生成客户端的薄封装。

## 登录说明

- 演示账号：`admin / password`
- 登录后 token 会保存在本地存储中
- Axios 拦截器会自动带上 `Authorization` 请求头

## 当前实现边界

- 用户页支持新增、删除、更新邮箱
- 订单页支持按后端预置商品创建订单
- 商品列表暂使用后端种子数据的固定映射：`1 Keyboard`、`2 Mouse`、`3 Monitor`
- 当前已引入生成客户端，但未启用前端类型检查，仅保证生成与构建链路可运行

如果后端后续新增商品查询接口，可以将这里的固定数据替换为实时获取。