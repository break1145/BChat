package com.bchat.ws;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChannelInactiveHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 获取客户端的 IP 地址或者其他信息，记录日志
        String clientIp = ctx.channel().remoteAddress().toString();
        log.info("Netty Websocket 客户端断开连接，IP 地址: {}", clientIp);

        // 调用父类的 channelInactive 事件
        super.channelInactive(ctx);
    }
}
