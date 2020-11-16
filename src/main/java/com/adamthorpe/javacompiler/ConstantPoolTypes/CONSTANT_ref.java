package com.adamthorpe.javacompiler.ConstantPoolTypes;

public abstract class CONSTANT_ref extends CONSTANT {

  protected byte[] class_index = new byte[2]; //u2
  protected byte[] name_and_type_index = new byte[2]; //u2

}
