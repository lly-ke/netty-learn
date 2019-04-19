package com.netty.demo.demo9;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.logging.Level;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-19 16:47
 **/
public class Server {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        ChannelFuture future =
                new ServerBootstrap().group(bossLoopGroup, workerLoopGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

//                        ---------------------------------------------
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new HttpObjectAggregator(8097));
                        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
//                        ---------------------------------------------
                        pipeline.addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
                                String text = msg.text();
                                System.out.println("服务端接受到信息:" + text);
                                ctx.channel().writeAndFlush(new TextWebSocketFrame(ctx.channel().remoteAddress().toString()));
                            }

                            @Override
                            public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("新连接添加:" + ctx.channel().id().asLongText());
                                ctx.channel().writeAndFlush("连接成功");
                            }

                            @Override
                            public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("连接断开:" + ctx.channel().id());
                                ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器发送连接断开信息"));
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                System.out.println("发生异常:" + cause);
                                ctx.channel().close();
                            }
                        });
                    }
                }).bind(8080).sync();
        future.channel().closeFuture().sync();

        bossLoopGroup.shutdownGracefully();
        workerLoopGroup.shutdownGracefully();
    }

}
