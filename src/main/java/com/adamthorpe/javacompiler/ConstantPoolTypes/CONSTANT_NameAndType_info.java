package com.adamthorpe.javacompiler.ConstantPoolTypes;

import com.adamthorpe.javacompiler.ByteConvert;

public class CONSTANT_NameAndType_info extends CONSTANT {
  
  protected byte[] name_index; //u2
  protected byte[] descriptor_index; //u2

  public CONSTANT_NameAndType_info(int name_index_pos, int descriptor_index_pos) {
    this.tag = Byte.parseByte("12");
    this.name_index = ByteConvert.intToBytes(2, name_index_pos);
    this.descriptor_index = ByteConvert.intToBytes(2, descriptor_index_pos);
  }

  @Override
  public byte[] getData() {
    byte[] data = new byte[5]; 
    
    System.arraycopy(super.getData(), 0, data, 0, 1);
    System.arraycopy(name_index, 0, data, 1, 2);
    System.arraycopy(descriptor_index, 0, data, 3, 2);

    return data;
  }

  @Override
  public int getLength() {
    return super.getLength() + 4;
  }
}
