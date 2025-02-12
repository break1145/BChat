## 顶层设计

设计参考mallchat

<img src="https://cdn.nlark.com/yuque/0/2023/jpeg/26318626/1692717057362-3de0b048-1963-4a27-9d6e-bf599ea8beff.jpeg?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_54%2Ctext_TWFsbENoYXQ%3D%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10" alt="img" style="zoom:150%;" />

`WebSocket`：维护和用户的连接通道，可以接收消息，也可以推送消息，为**有状态服务**

`IM服务`：负责消息的发送逻辑，处理单聊群聊的消息

`Logic服务`：处理用户的心跳，上下线，联系人，加好友，创群组等逻辑

`Auth服务`：处理用户认证，权限等需求

`Router`：推送消息时，不同用户在不同`WebSocket`服务上，确保正确推送，与可靠推送



交互的流程大致如下：

1. 用户A和`WebSocket`服务建立连接。之后都通过该连接发送消息，接受消息。
2. 用户A发送了一条群消息“在吗”，`WebSocket`服务将消息通过dubbo转发给`IM服务`，由于`IM服务`是无状态的，可以通过负载均衡随机发到某一台上。
3. `IM服务`将消息持久化，然后将消息投递到`消息队列MQ`，这样能快速响应前端，并且mq的消费者根据负载慢慢的进行后续的推送，写扩散等操作。
4. `消费者`会判断，根据是否热点群聊的消息做不同逻辑。如果是热点群聊，只写`热点信箱`。如果是单聊或者普通群聊，会写扩散到每个`群成员信箱`。这里假设是小群，会写入B和C的信箱。
5. 将消息投递信箱后，需要将消息推送给用户。这里可以根据是否在线，在线的进行`WebSocket`推送，离线的进行`push通知`。由于用户的连接在不同的`WebSocket`上，需要`Router`服务推送到B和C所在的不同`WebSocket`方案有两种，后续介绍。
6. 推送的时候需要确保消息的可靠性，如果保证一定推送成功？可能要做`应用层的ack`，类似tcp的滑动窗口确认。
7. 用户在查询自己的会话列表的时候，需要有一个`聚合层`聚合`用户信箱`，以及`热点信箱`。再严格排序后返回给用户。所谓之`推拉结合`。



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



netty启动后，要向pipeline加入一些自定义处理。

- **编解码器**需要用到`HttpServerCodec`。
-  `WebSocketServerProtocolHandler`是netty**进行websocket升级**的处理器。在这期间会抹除http相关的信息，比如请求头啥的。如果想获取相关信息，需要在这之前获取。
- `HttpHeadersHandler`是我们自己的处理器。赶在websocket升级之前，**获取用户的ip**地址，然后保存到channel的附件里
- `NettyWebSocketServerHandler`是我们的业务处理器，里面处理客户端的事件。
- `IdleStateHandler`实现**心跳检测**。





对于升级到websocket的请求，





