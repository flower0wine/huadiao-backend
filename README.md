# 1. 项目简介
首先这是一个纯个人开发的个人网站的后端代码库，前端代码库在这
[花凋前端代码库](https://github.com/flower0wine/huadiao-user)。

其次这个项目于 `2022年9月` 开始开发，一直到现在。

## 1.2. 项目功能

该项目的功能很多，下面就列举一些比较有代表性的功能。

- 使用 session 和 cookie 实现用户登录状态的保存。即不用重复登录。
- 使用 redis 生成并保存唯一键，比如每个用户都有一个唯一的 `uid`。
- 使用 elasticsearch 实现搜索功能，例如通过用户昵称来搜索用户。
- 使用原生的 JavaScript 和 spring 的 MultipartFile 实现用户的头像上传功能。
- 使用 WebSocket 和 spring 的 HttpSessionHandshakeInterceptor 和 WebSocketHandler
实现用户之间的私信。目前仅支持相互关注的用户之间进行通信。
- 实现第三方用户登录（Github、Google 登录方式）。

## 1.3. 项目技术栈

- Spring
- Mybatis 
- Elasticsearch
- Redis 
- Mysql 
- Slf4j

## 1.4. 架构

![架构图](images/architecture.png)


