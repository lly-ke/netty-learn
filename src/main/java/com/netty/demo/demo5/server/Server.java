package com.netty.demo.demo5.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.IOException;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-17 10:11
 **/
public class Server {
    private static ChannelGroup channelGroup =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void main(String[] args) throws IOException {
        EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup();


        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture =
                    serverBootstrap.group(bossEventLoopGroup, workerEventLoopGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            pipeline.addLast(new SimpleChannelInboundHandler<String>() {


                                @Override
                                public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println(ctx.channel().remoteAddress() + "      up");
                                    channelGroup.writeAndFlush("添加 [服务器] - " + ctx.channel().remoteAddress() + "\n");
                                    channelGroup.add(ctx.channel());
//                                    channelGroup.close();
                                }


                                @Override
                                public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                                    System.out.println(ctx.channel().remoteAddress() + "      down");
                                    channelGroup.remove(ctx.channel());
                                    channelGroup.writeAndFlush("断开 [服务器] - " + ctx.channel().remoteAddress() + "\n");
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                    System.out.println("服务端收到:" + msg);
                                    channelGroup.writeAndFlush(msg);
                                }
                            });

                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                        }
                    }).bind("127.0.0.1", 8080).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossEventLoopGroup.shutdownGracefully();
            workerEventLoopGroup.shutdownGracefully();
        }
    }


}
