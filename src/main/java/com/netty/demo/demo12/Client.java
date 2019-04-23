package com.netty.demo.demo12;

import com.netty.demo.demo12.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

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


        System.out.println("-----------客户端和服务端数据普通调用 start-------------------");
        for (int i = 0; i < 100; i++) {
            MyResponse response = studentServiceBlockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("爱的哇多").build());
            System.out.println("response = " + response.getRealname());
        }
        System.out.println("-----------客户端和服务端数据普通调用    end-------------------");


        System.out.println("---------------服务器返回流式数据    start----------------");
        Iterator<StudentResponse> student = studentServiceBlockingStub.getStudentByAge(StudentRequest.newBuilder().setAge(19).build());
        while (student.hasNext()) {//会阻塞, 直到服务器调用了onCompleted才会为false
            StudentResponse studentResponse = student.next();
            System.out.println("studentResponse = " + studentResponse);
        }
        System.out.println("---------------服务器返回流式数据   end----------------");

        System.out.println("---------------客户端流式数据       start----------------");
    }

}
