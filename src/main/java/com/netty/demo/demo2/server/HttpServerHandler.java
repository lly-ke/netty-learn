package com.netty.demo.demo2.server;

import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-16 13:43
 **/
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println(msg.getClass());

        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            System.out.println("----------------------------------------------------");
            System.out.println(request);
            System.out.println("----------------------------------------------------");

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    Unpooled.copiedBuffer("中文呢, 你开心吗?", CharsetUtil.UTF_8));

            HttpHeaders headers = response.headers();
            headers.set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            ctx.writeAndFlush(response);
            ctx.channel().close();
//            ctx.close();
        }
    }
}
