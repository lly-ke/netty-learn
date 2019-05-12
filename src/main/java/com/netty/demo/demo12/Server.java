package com.netty.demo.demo12;

import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-22 15:28
 **/
public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(8080);
        io.grpc.Server server = serverBuilder.addService(new StudentServiceImpl()).build().start();
        System.out.println("服务器8080端口");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.shutdown();
            }
        });

        server.awaitTermination();
    }


}
