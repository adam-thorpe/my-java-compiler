package com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries;

import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.3</p>
 */
public class CONSTANT_String_info extends CONSTANT {
  
  protected byte[] string_index; //u2

  public CONSTANT_String_info(int string_index_pos) {
    super(8);
    this.string_index = ByteConvert.intToBytes(2, string_index_pos);
  }

  @Override
  public byte[] getData() {
    return ByteConvert.copyBytes(tag, string_index);
  }

  @Override
  public int getLength() {
    return super.getLength() + string_index.length;
  }
}