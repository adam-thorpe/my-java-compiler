package com.adamthorpe.javacompiler.ConstantPoolTypes;

public class CONSTANT_Class_info extends CONSTANT {
  
  private byte[] name_index = new byte[2]; // u2

  public CONSTANT_Class_info() {
    this.tag = Byte.parseByte("7");
  }

  @Override
  public byte[] getData() {
    byte[] data = new byte[3]; 
    
    System.arraycopy(super.getData(), 0, data, 0, 1);
    System.arraycopy(name_index, 0, data, 1, 2);

    return data;
  }
}
