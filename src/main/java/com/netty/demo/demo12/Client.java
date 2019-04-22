package com.netty.demo.demo12;

import com.netty.demo.demo12.grpc.MyRequest;
import com.netty.demo.demo12.grpc.MyResponse;
import com.netty.demo.demo12.grpc.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-22 15:37
 **/
public class Client {


    public static void main(String[] args) {
        ManagedChannelBuilder<?> managedChannelBuilder = ManagedChannelBuilder.forAddress("localhost", 8080);

        ManagedChannel managedChannel = managedChannelBuilder.usePlaintext().build();

        StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);
        MyResponse response = studentServiceBlockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("爱的哇多").build());
        for (int i = 0; i < 100; i++) {
            System.out.println("response = " + response.getRealname());
        }
    }

}
