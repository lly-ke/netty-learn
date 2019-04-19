package com.netty.demo.dmeo3.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.util.concurrent.EventExecutorGroup;

import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-16 18:21
 **/
@ChannelHandler.Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("你快乐吗?\r\n");
//        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
//        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("客户端发送:" + msg);
        ctx.write("from server:" + UUID.randomUUID());
        ctx.flush();
//        ctx.channel().close();
        System.out.println("close");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常:" + cause);
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("数据读取完毕");
        ctx.flush();
//        super.channelReadComplete(ctx);
    }
}
