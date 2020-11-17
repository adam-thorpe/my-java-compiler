package com.adamthorpe.javacompiler.ConstantPoolTypes;

public class CONSTANT_Utf8_info extends CONSTANT {

  protected byte[] length = new byte[2]; //u2
  protected int lengthInt;
  protected byte[] bytes; //length

  public CONSTANT_Utf8_info() {
    this.tag = Byte.parseByte("1");
  }

  @Override
  public byte[] getData() {
    byte[] data = new byte[3+lengthInt];
    
    System.arraycopy(super.getData(), 0, data, 0, 1);
    System.arraycopy(length, 0, data, 1, 2);
    System.arraycopy(bytes, 0, data, 3, lengthInt);

    return data;
  }

}
