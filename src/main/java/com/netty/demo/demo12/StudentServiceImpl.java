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
        responseObserver.onCompleted();
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
                System.out.println("发生错误:" + t.getMessage());
            }

            @Override
            public void onCompleted() {
                StudentResponse studentResponse = StudentResponse.newBuilder().setName("张三").setAge(12).setCity("哈扎克").build();

                System.out.println("完成响应");
            }
        };
    }
}
