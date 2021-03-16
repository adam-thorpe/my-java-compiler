package com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries;

import com.adamthorpe.javacompiler.Utilities.ByteConvert;

public class CONSTANT_Class_info extends CONSTANT {
  
  private byte[] name_index; //u2

  public CONSTANT_Class_info(int name_index_pos) {
    super(7);
    this.name_index = ByteConvert.intToBytes(2, name_index_pos);
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(tag, name_index);
  }

  @Override
  public int getLength() {
    return super.getLength() + name_index.length;
  }
}