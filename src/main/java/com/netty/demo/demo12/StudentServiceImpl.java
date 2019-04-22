package com.netty.demo.demo12;

import com.netty.demo.demo12.grpc.MyRequest;
import com.netty.demo.demo12.grpc.MyResponse;
import com.netty.demo.demo12.grpc.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;

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
        System.out.println("request.getUsername() = " + request.getUsername());
        responseObserver.onNext(MyResponse.newBuilder().setRealname(request.getUsername() + UUID.randomUUID().toString()).build());
        responseObserver.onCompleted();
    }

}
