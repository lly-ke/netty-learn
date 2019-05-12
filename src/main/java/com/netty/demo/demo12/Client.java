package com.netty.demo.demo12;

import com.netty.demo.demo12.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.StreamObservers;

import javax.xml.bind.ValidationEvent;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-22 15:37
 **/
public class Client {


    public static void main(String[] args) throws InterruptedException {
        ManagedChannelBuilder<?> managedChannelBuilder = ManagedChannelBuilder.forAddress("localhost", 8080);

        ManagedChannel managedChannel = managedChannelBuilder.usePlaintext().build();

        StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub =
                StudentServiceGrpc.newBlockingStub(managedChannel);
        StudentServiceGrpc.StudentServiceStub studentServiceStub =
                StudentServiceGrpc.newStub(managedChannel);


        System.out.println("-----------客户端和服务端数据普通调用 start-------------------");
//        for (int i = 0; i < 100; i++) {
//            MyResponse response = studentServiceBlockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("爱的哇多").build());
//            System.out.println("response = " + response.getRealname());
//        }
        System.out.println("-----------客户端和服务端数据普通调用    end-------------------");


        System.out.println("---------------服务器返回流式数据    start----------------");
//        Iterator<StudentResponse> student = studentServiceBlockingStub.getStudentByAge(StudentRequest.newBuilder().setAge(19).build());
//        while (student.hasNext()) {//会阻塞, 直到服务器调用了onCompleted才会为false
//            StudentResponse studentResponse = student.next();
//            System.out.println("studentResponse = " + studentResponse);
//        }
        System.out.println("---------------服务器返回流式数据   end----------------");


        System.out.println("---------------客户端流式数据       start----------------");

        StreamObserver<StudentResponseList> streamObserver = new StreamObserver<StudentResponseList>() {

            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentResponseList().forEach((response) -> {
                    System.out.println("response.getAge() = " + response.getAge());
                });
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("error client:" + t.getMessage() + ", time=" +  System.nanoTime());
            }

            @Override
            public void onCompleted() {
                System.out.println("completed....");
            }
        };
        StreamObserver<StudentRequest> requestStreamObserver = studentServiceStub.getStudentWrapperByAges(streamObserver);

        for (int i = 0; i < 10; i++) {
            int i1 = new Scanner(System.in).nextInt();
            requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(i1).build());

        }
        requestStreamObserver.onCompleted();//客户端主动断开连接
        System.out.println("---------------客户端流式数据     end-------------------------");

        //客户端流式数据main需要阻塞不然jvm会退出
        Thread.sleep(Long.MAX_VALUE);
    }

}
