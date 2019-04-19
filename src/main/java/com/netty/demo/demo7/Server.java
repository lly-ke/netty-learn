package com.netty.demo.demo7;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import javafx.scene.chart.PieChart;

import java.util.concurrent.TimeUnit;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-19 01:27
 **/
public class Server {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup();

        ChannelFuture future =
                new ServerBootstrap().group(bossEventLoopGroup, workerEventLoopGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new IdleStateHandler(4, 4,4, TimeUnit.SECONDS));

                        pipeline.addLast(new ChannelInboundHandlerAdapter() {

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg instanceof HttpRequest);
                            }

                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                System.out.println(evt.getClass());
                                if (evt instanceof IdleStateEvent) {
                                    IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

                                    String stateString = null;
                                    switch (idleStateEvent.state()) {
                                        case READER_IDLE:
                                            stateString = "读空闲";
                                            break;
                                        case WRITER_IDLE:
                                            stateString = "写空闲";
                                            break;
                                        case ALL_IDLE:
                                            stateString = "读写空闲";
                                    }


                                    System.out.println(ch.remoteAddress() + " 超时类型:" + stateString);
//                                    ctx.channel().close();
                                }

                            }


                        });
                    }
                }).bind(8080);

        future.sync();


        future.channel().closeFuture().sync();

        bossEventLoopGroup.shutdownGracefully();
        workerEventLoopGroup.shutdownGracefully();
    }


}
