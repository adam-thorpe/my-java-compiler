package com.adamthorpe.javacompiler.Types.ConstantPool;

import com.adamthorpe.javacompiler.ByteConvert;

public class CONSTANT_String_info extends CONSTANT {
  
  protected byte[] string_index; //u2

  public CONSTANT_String_info(int string_index_pos) {
    this.tag = (byte) 8;
    this.string_index = ByteConvert.intToBytes(2, string_index_pos);
  }

  @Override
  public byte[] getData() {
    byte[] data = new byte[3]; 
    
    System.arraycopy(super.getData(), 0, data, 0, 1);
    System.arraycopy(string_index, 0, data, 1, 2);

    return data;
  }

  @Override
  public int getLength() {
    return super.getLength() + 2;
  }
}
