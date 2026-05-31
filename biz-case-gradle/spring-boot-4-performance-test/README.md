# spring boot 4 performance test

此project为用于性能测试的demo，主要测试同步结果返回以及流式返回两种不同类型接口。

## 测试用例
### 功能测试
1. 同步返回：
```shell
curl -X POST "http://localhost:8080/performance/test/test1" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer your-token" \
  -H "X-Request-ID: 123456" \
  -d '{
    "userId": 1001,
    "action": "query",
    "data": {
      "key1": "value1",
      "key2": 123
    }
  }'
```

2. 流式返回：

```shell
# 一次性输出
# 必须添加 -H "Accept: text/event-stream" 明确声明接受 SSE
# curl 会持续输出 event 流，直到服务端关闭或超时（60秒）
curl -X POST "http://localhost:8080/performance/test/test2" \
  -H "Content-Type: application/json" \
  -H "Accept: text/event-stream" \
  -H "X-Client-ID: client-001" \
  -d '{
    "taskId": "task-789",
    "operation": "stream-process",
    "config": {
      "timeout": 30,
      "retry": 3
    }
  }'
  
# SSE
curl --no-buffer -X POST "http://localhost:8080/performance/test/test2" \
  -H "Content-Type: application/json" \
  -H "Accept: text/event-stream" \
  -H "X-Client-ID: client-001" \
  -d '{
    "taskId": "task-789",
    "operation": "stream-process",
    "config": {
      "timeout": 30,
      "retry": 3
    }
  }'
```

### 性能测试
使用JMeter进行性能测试，基本配置如下：
