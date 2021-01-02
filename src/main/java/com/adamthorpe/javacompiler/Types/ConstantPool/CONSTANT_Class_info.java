package com.adamthorpe.javacompiler.Types.ConstantPool;

import com.adamthorpe.javacompiler.ByteConvert;

public class CONSTANT_Class_info extends CONSTANT {
  
  private byte[] name_index; //u2

  public CONSTANT_Class_info(int name_index_pos) {
    this.tag = (byte) 7;
    this.name_index = ByteConvert.intToBytes(2, name_index_pos);
  }

  @Override
  public byte[] getData() {
    byte[] data = new byte[3];
    
    System.arraycopy(super.getData(), 0, data, 0, 1);
    System.arraycopy(name_index, 0, data, 1, 2);

    return data;
  }

  @Override
  public int getLength() {
    return super.getLength() + 2;
  }
}