// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: classes/grpc/Student.proto

package com.netty.demo.demo12.grpc;

public interface StudentResponseListOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.netty.demo.grpc.StudentResponseList)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .com.netty.demo.grpc.StudentResponse studentResponse = 1;</code>
   */
  java.util.List<StudentResponse>
      getStudentResponseList();
  /**
   * <code>repeated .com.netty.demo.grpc.StudentResponse studentResponse = 1;</code>
   */
  StudentResponse getStudentResponse(int index);
  /**
   * <code>repeated .com.netty.demo.grpc.StudentResponse studentResponse = 1;</code>
   */
  int getStudentResponseCount();
  /**
   * <code>repeated .com.netty.demo.grpc.StudentResponse studentResponse = 1;</code>
   */
  java.util.List<? extends StudentResponseOrBuilder>
      getStudentResponseOrBuilderList();
  /**
   * <code>repeated .com.netty.demo.grpc.StudentResponse studentResponse = 1;</code>
   */
  StudentResponseOrBuilder getStudentResponseOrBuilder(
          int index);
}
