package com.adamthorpe.javacompiler.ConstantPoolTypes;

public class CONSTANT_Class_info extends CONSTANT {
  
  private byte[] name_index = new byte[2]; // u2

  public CONSTANT_Class_info() {
    this.tag = Byte.parseByte("7");
  }
}
