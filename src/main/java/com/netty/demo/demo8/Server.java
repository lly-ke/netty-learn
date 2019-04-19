package com.netty.demo.demo8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.time.LocalDateTime;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-19 14:23
 **/
public class Server {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup();

        ChannelFuture future =
                new ServerBootstrap().channel(NioServerSocketChannel.class).group(bossEventLoopGroup,
                        workerEventLoopGroup).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new HttpObjectAggregator(8192));
                        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                        pipeline.addLast(new SimpleChannelInboundHandler<TextWebSocketFrame>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
                                System.out.println("收到消息:" + msg.text());

                                ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间:" + LocalDateTime.now()));
                            }

                            @Override
                            public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("Server.handlerAdded:" + ctx.channel().id().asShortText());
                                System.out.println("Server.handlerAdded:" + ctx.channel().id().asLongText());
                            }

                            @Override
                            public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                                System.out.println("Server.handlerRemoved:" + ctx.channel().id());
                                ctx.close();
                            }


                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                System.out.println("发生错误:" + cause);
                                System.out.println("发生错误:" + cause.getMessage());
                                ctx.close();
                            }
                        });
                    }
                }).bind(8080).sync();
        future.channel().closeFuture().sync();


        bossEventLoopGroup.shutdownGracefully();
        workerEventLoopGroup.shutdownGracefully();
    }

}
