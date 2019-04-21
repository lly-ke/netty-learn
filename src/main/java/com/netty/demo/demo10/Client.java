package com.netty.demo.demo10;

import com.netty.demo.demo6.SimpleChatClientInitializer;
import com.netty.demo.protobuf.DataInfo;
import com.netty.demo.protobuf.MyDataInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.awt.*;
import java.util.Random;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-21 16:08
 **/
public class Client {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup();

        ChannelFuture future = new Bootstrap()
                .group(workerEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ProtobufVarint32FrameDecoder());
                        pipeline.addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                        pipeline.addLast(new ProtobufEncoder());
                        pipeline.addLast(new SimpleChannelInboundHandler<MyDataInfo.MyMessage>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
                                System.out.println("客户端:" + msg);
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                DataInfo.Student student = DataInfo.Student.newBuilder().setName("阿瓦达").setAge(12123).build();
//                                ctx.channel().writeAndFlush(student);


                                for (int i = 0; i < 10; i++) {

                                    int j = new Random().nextInt(3);
                                    MyDataInfo.MyMessage message = null;
                                    switch (j){
                                        case 0:
                                            message = MyDataInfo.MyMessage.newBuilder()
                                                    .setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                                                    .setPerson(
                                                            MyDataInfo.Person.newBuilder()
                                                                    .setAge(12).setName("张三").setAddress("中国")).build();
                                            break;
                                        case 1:
                                            message = MyDataInfo.MyMessage.newBuilder()
                                                    .setDataType(MyDataInfo.MyMessage.DataType.DogType)
                                                    .setDog(
                                                            MyDataInfo.Dog.newBuilder()
                                                                    .setAge(12).setName("狗哥")).build();
                                            break;
                                        case 2:
                                            message = MyDataInfo.MyMessage.newBuilder()
                                                    .setDataType(MyDataInfo.MyMessage.DataType.CatType)
                                                    .setCat(
                                                            MyDataInfo.Cat.newBuilder()
                                                                    .setName("猫哥").setCity("长沙市")).build();
                                            break;
                                    }

                                    ctx.channel().writeAndFlush(message);
                                }
                            }
                        });
                    }
                })
                .connect("127.0.0.1", 8080).sync();
        future.channel().closeFuture().sync();

        workerEventLoopGroup.shutdownGracefully();
    }

}
