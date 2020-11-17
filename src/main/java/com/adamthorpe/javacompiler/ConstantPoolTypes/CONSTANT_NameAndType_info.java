package com.adamthorpe.javacompiler.ConstantPoolTypes;

public class CONSTANT_NameAndType_info extends CONSTANT {
  
  protected byte[] name_index = new byte[2];
  protected byte[] descriptor_index = new byte[2];

  public CONSTANT_NameAndType_info() {
    this.tag = Byte.parseByte("12");
  }

  @Override
  public byte[] getData() {
    byte[] data = new byte[5]; 
    
    System.arraycopy(super.getData(), 0, data, 0, 1);
    System.arraycopy(name_index, 0, data, 1, 2);
    System.arraycopy(descriptor_index, 0, data, 3, 2);

    return data;
  }
}
