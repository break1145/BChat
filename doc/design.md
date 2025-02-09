## 整体设计

## 模块划分

- 用户模块：

  包括用户注册登录、鉴权

- 消息发送-接收模块：

  项目核心功能，使用netty-websocket实现消息收发

- 群组模块：

  除了消息一对一收发，还要支持群聊，以及用户之间关系的处理

- 统一后台管理：

  反正都是操作同一个数据库，考虑直接用django快速出一套，或者在原来的地方写。



### 用户模块

用户表设计：

```sql
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
```

暂时只有name（昵称）username（账号）password（密码）email（邮件）

其中，密码做加密处理，使用spring security框架

### 消息模块

消息表设计：

```sql
CREATE TABLE
```



#### websocket模块

无论是长轮询还是短轮询，都会导致大量无效请求，后端压力大。所以用websocket

原理：客户端和服务器之间维持一个 TCP/IP 长连接，全双工通道



websocket可以用tomcat或netty实现，这里使用netty。原因：

- netty提供了很多好用的api，有强大的编解码器和处理器，可以轻松处理复杂的协议和数据格式.
- 扩展性好.比如 可以使用pipeline方便的进行前置后置的处理，可以用netty的心跳处理器来检查连接的状态

实现参考[netty对websocket协议的实现_netty实现websocket-CSDN博客](https://blog.csdn.net/mahao25/article/details/127418543)



websocket工作流程：

