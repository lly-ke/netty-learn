package com.netty.demo.demo12;

import com.netty.demo.demo12.grpc.*;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.StreamObservers;

import java.util.UUID;

/**
 * @program: demo7
 * @description:
 * @author: liuwei
 * @create: 2019-04-22 14:45
 **/
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase{

    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("StudentServiceImpl.getRealNameByUsername");
        System.out.println("request.getUsername() = " + request.getUsername());
        responseObserver.onNext(MyResponse.newBuilder().setRealname(request.getUsername() + UUID.randomUUID().toString()).build());

        responseObserver.onCompleted();
    }

    @Override
    public void getStudentByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("StudentServiceImpl.getStudentByAge");
        int age = request.getAge();
        StudentResponse studentResponse = StudentResponse.newBuilder()
                .setAge(age)
                .setCity("阿瓦达")
                .setName("爱的哇多无")
                .build();

        responseObserver.onNext(studentResponse);
        responseObserver.onCompleted();//服务端主动断开连接
    }

    @Override
    public StreamObserver<StudentRequest> getStudentWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("value = " + value);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("server 发生错误:" + t.getMessage() + ", time=" +  System.nanoTime());
            }

            @Override
            public void onCompleted() {//服务端接受来自客户端的断开请求调用
                System.out.println("responseObserver.getClass() = " + responseObserver.getClass());
                responseObserver.onNext(StudentResponseList
                        .newBuilder().addStudentResponse(StudentResponse.newBuilder().setAge(1).build()).build());
                responseObserver.onNext(StudentResponseList
                        .newBuilder().addStudentResponse(StudentResponse.newBuilder().setAge(2).build()).build());
                //该条数据不会返回给客户端
                responseObserver.onCompleted();//服务端也断开连接
            }
        };
    }
}
