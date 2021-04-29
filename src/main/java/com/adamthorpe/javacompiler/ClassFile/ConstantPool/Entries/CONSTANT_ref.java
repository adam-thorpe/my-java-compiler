package com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries;

import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.2</p>
 */
public abstract class CONSTANT_ref extends CONSTANT {

  protected byte[] class_index;         //u2
  protected byte[] name_and_type_index; //u2

  public CONSTANT_ref(int tag, int class_index_pos, int name_and_type_index_pos) {
    super(tag);
    this.class_index = ByteConvert.intToBytes(2, class_index_pos);
    this.name_and_type_index = ByteConvert.intToBytes(2, name_and_type_index_pos);
  }

  @Override
  public byte[] getData() {
    return ByteConvert.copyBytes(tag, class_index, name_and_type_index);
  }

  @Override
  public int getLength() {
    return super.getLength() +
      class_index.length +
      name_and_type_index.length;
  }
}