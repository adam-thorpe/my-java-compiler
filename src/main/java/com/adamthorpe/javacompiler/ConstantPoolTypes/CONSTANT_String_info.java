package com.adamthorpe.javacompiler.ConstantPoolTypes;

public class CONSTANT_String_info extends CONSTANT {
  
  protected byte[] string_index = new byte[2]; //u2

  public CONSTANT_String_info() {
    this.tag = Byte.parseByte("8");
  }

  @Override
  public byte[] getData() {
    byte[] data = new byte[3]; 
    
    System.arraycopy(super.getData(), 0, data, 0, 1);
    System.arraycopy(string_index, 0, data, 1, 2);

    return data;
  }
}
