// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user2.proto

package com.distSCA.service2_prox;

public final class MathServiceImpl {
  private MathServiceImpl() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ConvertMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ConvertMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ConvertResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ConvertResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_RandomRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_RandomRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_NumberResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_NumberResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CalculateRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CalculateRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CalculateResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CalculateResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_NumberMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_NumberMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013user2.proto\"B\n\016ConvertMessage\022\016\n\006numbe" +
      "r\030\001 \001(\t\022\020\n\010fromBase\030\002 \001(\005\022\016\n\006toBase\030\003 \001(" +
      "\005\"/\n\017ConvertResponse\022\016\n\006number\030\001 \001(\t\022\014\n\004" +
      "base\030\002 \001(\005\":\n\rRandomRequest\022\017\n\007numbers\030\001" +
      " \001(\005\022\013\n\003min\030\002 \001(\005\022\013\n\003max\030\003 \001(\005\" \n\016Number" +
      "Response\022\016\n\006number\030\001 \001(\005\"\262\001\n\020CalculateRe" +
      "quest\022\017\n\007number1\030\001 \001(\002\022\017\n\007number2\030\002 \001(\002\022" +
      ".\n\toperation\030\003 \001(\0162\033.CalculateRequest.Op" +
      "eration\"L\n\tOperation\022\014\n\010ADDITION\020\000\022\017\n\013SU" +
      "BTRACTION\020\001\022\022\n\016MULTIPLICATION\020\002\022\014\n\010DIVIS" +
      "ION\020\003\"4\n\021CalculateResponse\022\016\n\006result\030\001 \001" +
      "(\002\022\017\n\007message\030\002 \001(\t\"\037\n\rNumberMessage\022\016\n\006" +
      "number\030\001 \001(\0022\362\001\n\013MathService\0224\n\tcalculat" +
      "e\022\021.CalculateRequest\032\022.CalculateResponse" +
      "\"\000\022<\n\025generateRandomNumbers\022\016.RandomRequ" +
      "est\032\017.NumberResponse\"\0000\001\0227\n\raverageValue" +
      "s\022\016.NumberMessage\032\022.CalculateResponse\"\000(" +
      "\001\0226\n\013convertBase\022\017.ConvertMessage\032\020.Conv" +
      "ertResponse\"\000(\0010\001B.\n\031com.distSCA.service" +
      "2_proxB\017MathServiceImplP\001b\006proto3"
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
    internal_static_ConvertMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ConvertMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ConvertMessage_descriptor,
        new java.lang.String[] { "Number", "FromBase", "ToBase", });
    internal_static_ConvertResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_ConvertResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ConvertResponse_descriptor,
        new java.lang.String[] { "Number", "Base", });
    internal_static_RandomRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_RandomRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_RandomRequest_descriptor,
        new java.lang.String[] { "Numbers", "Min", "Max", });
    internal_static_NumberResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_NumberResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_NumberResponse_descriptor,
        new java.lang.String[] { "Number", });
    internal_static_CalculateRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_CalculateRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CalculateRequest_descriptor,
        new java.lang.String[] { "Number1", "Number2", "Operation", });
    internal_static_CalculateResponse_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_CalculateResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CalculateResponse_descriptor,
        new java.lang.String[] { "Result", "Message", });
    internal_static_NumberMessage_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_NumberMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_NumberMessage_descriptor,
        new java.lang.String[] { "Number", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}