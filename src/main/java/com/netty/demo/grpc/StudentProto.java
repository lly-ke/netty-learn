    // Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Student.proto

package com.netty.demo.grpc;

public final class StudentProto {
  private StudentProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_netty_demo_grpc_MyRequest_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_netty_demo_grpc_MyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_netty_demo_grpc_MyResponse_descriptor;
  static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_netty_demo_grpc_MyResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\rStudent.proto\022\023com.netty.demo.grpc\"\035\n\t" +
      "MyRequest\022\020\n\010username\030\001 \001(\t\"\036\n\nMyRespons" +
      "e\022\020\n\010realname\030\001 \001(\t2j\n\016StudentService\022X\n" +
      "\025GetRealNameByUsername\022\036.com.netty.demo." +
      "grpc.MyRequest\032\037.com.netty.demo.grpc.MyR" +
      "esponseB%\n\023com.netty.demo.grpcB\014StudentP" +
      "rotoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_netty_demo_grpc_MyRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_netty_demo_grpc_MyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_netty_demo_grpc_MyRequest_descriptor,
        new String[] { "Username", });
    internal_static_com_netty_demo_grpc_MyResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_netty_demo_grpc_MyResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_netty_demo_grpc_MyResponse_descriptor,
        new String[] { "Realname", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}