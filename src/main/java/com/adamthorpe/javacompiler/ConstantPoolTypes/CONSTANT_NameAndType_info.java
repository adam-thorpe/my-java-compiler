package com.adamthorpe.javacompiler.ConstantPoolTypes;

public class CONSTANT_NameAndType_info extends CONSTANT {
  protected byte[] name_index = new byte[2];
  protected byte[] descriptor_index = new byte[2];

  public CONSTANT_NameAndType_info() {
    this.tag = Byte.parseByte("12");
  }
}
