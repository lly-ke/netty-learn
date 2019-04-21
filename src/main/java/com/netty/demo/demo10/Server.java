package com.netty.demo.demo10;

import com.netty.demo.protobuf.DataInfo;
import com.netty.demo.protobuf.MyDataInfo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import javax.swing.text.AbstractDocument;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-21 15:56
 **/
public class Server {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup();

        ChannelFuture future = new ServerBootstrap().channel(NioServerSocketChannel.class).group(bossEventLoopGroup,
                workerEventLoopGroup)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        System.out.println("Server.initChannel");
                        pipeline.addLast(new ProtobufVarint32FrameDecoder());
                        pipeline.addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                        pipeline.addLast(new ProtobufEncoder());

                        pipeline.addLast(new SimpleChannelInboundHandler<MyDataInfo.MyMessage>() {

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
//                                System.out.println("msg.getName() = " + msg.getName());
//                                System.out.println("msg.getAge() = " + msg.getAge());
//                                System.out.println("msg.getAddress() = " + msg.getAddress());


                                switch (msg.getDataType()){
                                    case PersonType:
                                        MyDataInfo.Person person = msg.getPerson();
                                        System.out.println("person = " + person);
                                        break;
                                    case DogType:
                                        MyDataInfo.Dog dog = msg.getDog();
                                        System.out.println("dog = " + dog);
                                        break;
                                    case CatType:
                                        MyDataInfo.Cat cat = msg.getCat();
                                        System.out.println("cat = " + cat);
                                        break;
                                }

                                System.out.println("服务端:" + msg);
                            }
                        });
                    }
                }).bind(8080).sync();

        future.channel().closeFuture().sync();

        bossEventLoopGroup.shutdownGracefully();
        workerEventLoopGroup.shutdownGracefully();
    }

}
