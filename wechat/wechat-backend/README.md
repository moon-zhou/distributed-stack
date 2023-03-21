# wechat biz
实际工作中，涉及到微信小程序相关功能的开发，抽取核心示例代码。

## 微信通知
### 小程序后台配置操作

### 代码实现逻辑
1. 获取被通知人在该小程序下的openId
2. 获取access_token：[wechat api](https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-access-token/getAccessToken.html)
3. 调用小程序消息接口的发送：[wechat api](https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/mp-message-management/subscribe-message/sendMessage.html)