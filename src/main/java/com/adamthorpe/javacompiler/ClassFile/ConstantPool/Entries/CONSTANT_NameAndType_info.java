package com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries;

import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.6</p>
 */
public class CONSTANT_NameAndType_info extends CONSTANT {
  
  protected byte[] name_index;        //u2
  protected byte[] descriptor_index;  //u2

  public CONSTANT_NameAndType_info(int name_index_pos, int descriptor_index_pos) {
    super(12);
    this.name_index = ByteConvert.intToBytes(2, name_index_pos);
    this.descriptor_index = ByteConvert.intToBytes(2, descriptor_index_pos);
  }

  @Override
  public byte[] getData() {
    return ByteConvert.copyBytes(tag, name_index, descriptor_index);
  }

  @Override
  public int getLength() {
    return super.getLength() +
      name_index.length +
      descriptor_index.length;
  }
}