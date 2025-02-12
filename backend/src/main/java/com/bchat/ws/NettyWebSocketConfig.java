package com.bchat.ws;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyWebSocketConfig {
    @Bean
    public EventLoopGroup bossGroup() {
        return new NioEventLoopGroup(1);  // 处理连接请求的线程组
    }

    @Bean
    public EventLoopGroup workerGroup() {
        return new NioEventLoopGroup();  // 处理数据读写的线程组
    }

    @Bean
    public ChannelInitializer<SocketChannel> channelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast(new HttpServerCodec())  // HTTP解码
                        .addLast(new ChunkedWriteHandler())  // 文件传输
                        .addLast(new HttpObjectAggregator(8096))  // WebSocket帧解码
                        .addLast(new HttpHeadersHandler())  // 自定义处理器 获取ip、token
                        .addLast(new WebSocketServerProtocolHandler("/ws"))  // WebSocket协议处理
                        .addLast(new LoggingHandler()) // 日志处理
                        .addLast(new NettyWebSocketServerHandler())  // 处理自定义消息
                        .addLast(new ChannelInactiveHandler()); // 断开连接监听器
            }
        };
    }
}
