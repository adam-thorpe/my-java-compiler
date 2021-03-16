package com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries;

import com.adamthorpe.javacompiler.Utilities.ByteConvert;

public class CONSTANT_String_info extends CONSTANT {
  
  protected byte[] string_index; //u2

  public CONSTANT_String_info(int string_index_pos) {
    super(8);
    this.string_index = ByteConvert.intToBytes(2, string_index_pos);
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(tag, string_index);
  }

  @Override
  public int getLength() {
    return super.getLength() + string_index.length;
  }
}