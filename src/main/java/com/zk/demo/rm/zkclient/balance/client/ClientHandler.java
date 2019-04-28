package com.zk.demo.rm.zkclient.balance.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 创建一个处理器，来处理信道事件
 *
 * @author Gerry
 * @date 2019/01/04 11:02
 */
public class ClientHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
