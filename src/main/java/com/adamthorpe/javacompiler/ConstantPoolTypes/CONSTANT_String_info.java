package com.adamthorpe.javacompiler.ConstantPoolTypes;

public class CONSTANT_String_info extends CONSTANT {
  protected byte[] string_index = new byte[2]; //u2

  public CONSTANT_String_info() {
    this.tag = Byte.parseByte("8");
  }
}
