# Spring Boot Demo 项目

## 项目简介

本项目是一个基于 Spring Boot 3.2.5 的示例应用，集成了多种常用技术和框架，展示了企业级应用的核心功能模块。项目使用 H2 内存数据库，便于快速启动和测试。

## 技术栈

- **框架**: Spring Boot 3.2.5
- **ORM**: MyBatis Plus 3.5.6
- **数据库**: H2 Database (内存模式)
- **Excel 处理**: EasyExcel 3.3.4
- **工具库**: Hutool 5.8.26
- **线程上下文传递**: Alibaba Transmittable Thread Local (TTL) 2.14.5
- **日志**: Logback
- **其他**: Lombok, Spring AOP

## 核心功能模块

### 1. 用户管理 (User Management)

提供用户的 CRUD 操作，支持按名称搜索。

**实体字段**:
- `id`: 主键（自增）
- `name`: 姓名
- `email`: 邮箱
- `phone`: 电话
- `age`: 年龄
- `status`: 状态
- `deleted`: 逻辑删除标识
- `createTime` / `updateTime`: 自动填充的时间戳

### 2. 订单管理 (Order Management)

提供订单的 CRUD 操作，支持订单项（OrderItem）的关联查询和管理。

**实体字段**:
- `id`: 主键（自增）
- `orderNo`: 订单号
- `userId`: 用户 ID
- `totalAmount`: 订单总金额（自动计算）
- `status`: 订单状态
- `deleted`: 逻辑删除标识
- `items`: 订单项列表（非数据库字段）

### 3. Excel 导入导出

支持用户数据和订单数据的 Excel 导入导出功能。

**功能特性**:
- 用户数据导出/导入
- 订单数据导出/导入（支持 JSON 格式存储订单项）
- 使用 EasyExcel 实现高效处理

### 4. 异步任务处理

演示了线程池配置和异步任务执行，支持 TraceID 在多线程间的传递。

**核心特性**:
- 自定义线程池配置（核心线程数 5，最大线程数 10）
- TTL (Transmittable Thread Local) 支持
- MDC TraceID 跨线程传递
- 并行任务和异步任务两种模式

### 5. AOP 日志切面

实现了 Controller 层的统一日志记录。

**日志内容**:
- 请求方法、URL
- 请求参数
- 响应结果
- 执行耗时
- 异常信息

### 6. TraceID 链路追踪

通过 Filter 实现请求链路追踪。

**实现方式**:
- 每个请求生成唯一 TraceID
- 通过 MDC 存储和传递
- 在日志中输出 TraceID
- 支持跨线程传递

## 项目架构

```
com.example.demo
├── aspect/              # AOP 切面类
│   ├── ControllerLogAspect.java    # Controller 日志切面
│   ├── MapperLogAspect.java        # Mapper 日志切面
│   └── ServiceLogAspect.java       # Service 日志切面
├── common/              # 公共类
│   ├── Result.java                 # 统一返回结果
│   ├── OrderExcelDTO.java          # 订单 Excel 数据传输对象
│   └── UserExcelDTO.java           # 用户 Excel 数据传输对象
├── config/              # 配置类
│   ├── MyBatisPlusConfig.java      # MyBatis Plus 配置
│   ├── ThreadPoolConfig.java       # 线程池配置
│   └── WebMvcConfig.java           # Web MVC 配置
├── controller/          # 控制器层
│   ├── UserController.java         # 用户接口
│   ├── OrderController.java        # 订单接口
│   └── ExcelController.java        # Excel 接口
├── entity/              # 实体类
│   ├── User.java                   # 用户实体
│   ├── Order.java                  # 订单实体
│   └── OrderItem.java              # 订单项实体
├── filter/              # 过滤器
│   └── TraceIdFilter.java          # TraceID 过滤器
├── mapper/              # 数据访问层
│   ├── UserMapper.java
│   ├── OrderMapper.java
│   └── OrderItemMapper.java
├── service/             # 业务服务层
│   ├── UserService.java
│   ├── OrderService.java
│   ├── ExcelService.java
│   ├── AsyncService.java
│   └── impl/                       # 服务实现类
├── util/                # 工具类
│   └── TraceIdUtil.java            # TraceID 工具类
└── DemoApplication.java            # 启动类
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+

### 启动项目

```bash
cd biz-case/spring-boot-demo
mvn spring-boot:run
```

项目默认运行在 `http://localhost:8080`

### 访问 H2 控制台

```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
用户名：sa
密码：(空)
```

## API 接口测试

### 1. 用户管理接口

#### 1.1 获取用户列表
```bash
curl -X GET http://localhost:8080/api/users
```

#### 1.2 根据 ID 获取用户
```bash
curl -X GET http://localhost:8080/api/users/1
```

#### 1.3 按名称搜索用户
```bash
curl -X GET "http://localhost:8080/api/users/search?name=张"
```

#### 1.4 创建用户
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "张三",
    "email": "zhangsan@example.com",
    "phone": "13800138000",
    "age": 25,
    "status": 1
  }'
```

#### 1.5 更新用户
```bash
curl -X PUT http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "name": "张三丰",
    "email": "zhangsanfeng@example.com",
    "phone": "13800138000",
    "age": 26,
    "status": 1
  }'
```

#### 1.6 删除用户（逻辑删除）
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

#### 1.7 异步任务测试
```bash
curl -X POST http://localhost:8080/api/users/async-test
```

### 2. 订单管理接口

#### 2.1 获取订单列表
```bash
curl -X GET http://localhost:8080/api/orders
```

#### 2.2 根据 ID 获取订单
```bash
curl -X GET http://localhost:8080/api/orders/1
```

#### 2.3 获取订单的项目列表
```bash
curl -X GET http://localhost:8080/api/orders/1/items
```

#### 2.4 创建订单（含订单项）
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderNo": "ORDER20240309001",
    "userId": 1,
    "status": 1,
    "items": [
      {
        "productName": "商品 A",
        "price": 100.00,
        "quantity": 2
      },
      {
        "productName": "商品 B",
        "price": 50.00,
        "quantity": 3
      }
    ]
  }'
```

#### 2.5 更新订单
```bash
curl -X PUT http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "orderNo": "ORDER20240309001",
    "userId": 1,
    "status": 2,
    "items": [
      {
        "productName": "商品 C",
        "price": 200.00,
        "quantity": 1
      }
    ]
  }'
```

#### 2.6 删除订单
```bash
curl -X DELETE http://localhost:8080/api/orders/1
```

### 3. Excel 导入导出接口

#### 3.1 导出用户数据
```bash
curl -X GET http://localhost:8080/api/excel/users/export \
  --output users.xlsx
```

#### 3.2 导入用户数据
```bash
curl -X POST http://localhost:8080/api/excel/users/import \
  -F "file=@/path/to/users.xlsx"
```

#### 3.3 导出订单数据
```bash
curl -X GET http://localhost:8080/api/excel/orders/export \
  --output orders.xlsx
```

#### 3.4 导入订单数据
```bash
curl -X POST http://localhost:8080/api/excel/orders/import \
  -F "file=@/path/to/orders.xlsx"
```

## 配置说明

### application.yml 主要配置

```yaml
server:
  port: 8080

spring:
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:mem:testdb;MODE=MySQL
  h2:
    console:
      enabled: true
      path: /h2-console

mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0
```

### 线程池配置

- **核心线程数**: 5
- **最大线程数**: 10
- **队列容量**: 100
- **线程名前缀**: async-thread-
- **存活时间**: 60 秒

## 后续扩展原则

### 1. 代码规范

- **命名规范**: 
  - 变量使用 camelCase
  - 类名使用 PascalCase
  - 常量使用 UPPER_CASE
- **注释规范**: 
  - 代码注释优先使用英文
  - 特殊业务逻辑可使用中文注释
- **分层架构**: 严格遵循 Controller -> Service -> Mapper 三层架构

### 2. 事务管理

- 涉及多表操作的 Service 方法需添加 `@Transactional` 注解
- 事务注解只能加在 public 方法上
- 避免在事务内执行耗时操作（如 HTTP 请求）

### 3. 日志规范

- 使用 SLF4J + Logback
- 日志级别选择:
  - ERROR: 错误信息，需要人工介入
  - WARN: 警告信息，不影响系统运行
  - INFO: 重要流程信息
  - DEBUG: 调试信息
- 日志必须包含 TraceID 便于链路追踪

### 4. 异常处理

- Controller 层不捕获具体业务异常，统一由全局异常处理器处理
- Service 层只抛出运行时异常
- 自定义异常需继承 RuntimeException

### 5. 数据库操作

- 使用 MyBatis Plus 提供的 CRUD 方法
- 复杂查询使用 LambdaQueryWrapper
- 禁止在循环中执行 SQL 查询
- 一对多关系在 Service 层组装

### 6. 异步任务

- 异步方法必须使用线程池，禁止手动创建线程
- 异步方法必须是 public 且非 static
- 需要在异步方法中传递上下文信息时，使用 TTL 工具
- 异步任务需要记录 TraceID 便于问题排查

### 7. Excel 处理

- 导入导出使用 DTO 对象而非 Entity
- 大数据量导入导出使用监听器分批处理
- 文件名需要 URLEncode 处理

### 8. 逻辑删除

- 所有业务表必须包含 deleted 字段
- 使用 MyBatis Plus 的逻辑删除功能
- 物理删除仅限管理员操作或数据清理任务

### 9. 接口设计

- RESTful 风格
- 统一返回 Result 包装类
- GET: 查询操作
- POST: 新增操作
- PUT: 全量更新
- DELETE: 删除操作

### 10. 性能优化

- 列表查询注意分页
- 避免 N+1 查询问题
- 热点数据考虑缓存
- 批量操作使用批处理方法

## 常见问题

### Q: 如何切换为真实数据库？

A: 修改 `application.yml` 中的数据库配置，并添加对应数据库驱动依赖：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/your_database
    username: root
    password: your_password
```

### Q: 如何禁用 H2 控制台？

A: 修改 `application.yml`:

```yaml
spring:
  h2:
    console:
      enabled: false
```

### Q: 如何调整日志级别？

A: 修改 `application.yml`:

```yaml
logging:
  level:
    com.example.demo: DEBUG  # 可调整为 INFO, WARN, ERROR
```

### Q: 异步任务不执行怎么办？

A: 确保:
1. 启动类添加了 `@EnableAsync` 注解
2. 异步方法所在的类被 Spring 管理（添加了 `@Service` 等注解）
3. 调用方不能是同一个类中的方法（需要通过注入的方式调用）

## 参考资料

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [MyBatis Plus 官方文档](https://baomidou.com/)
- [EasyExcel 官方文档](https://easyexcel.opensource.alibaba.com/)
- [H2 Database 官方文档](http://www.h2database.com/)
