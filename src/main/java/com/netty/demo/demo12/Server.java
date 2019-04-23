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
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                server.shutdown();
                System.err.println("*** server shut down");
            }
        });

        server.awaitTermination();
    }


}
