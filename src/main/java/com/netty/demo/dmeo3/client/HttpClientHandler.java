package com.netty.demo.dmeo3.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import sun.util.resources.cldr.tg.CalendarData_tg_Cyrl_TJ;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-17 01:07
 **/
@ChannelHandler.Sharable
public class HttpClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
        ctx.writeAndFlush("客户端数据");
//        ctx.channel().closeFuture().sync();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("问候");
        super.channelActive(ctx);
    }
}
