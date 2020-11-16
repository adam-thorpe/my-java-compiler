package com.adamthorpe.javacompiler.ConstantPoolTypes;

public class CONSTANT_Utf8_info extends CONSTANT {
  protected byte[] length = new byte[2]; //u2
  protected byte[] bytes; //length

  public CONSTANT_Utf8_info() {
    this.tag = Byte.parseByte("1");
  }
}
