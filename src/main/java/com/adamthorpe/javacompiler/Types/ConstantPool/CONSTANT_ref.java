package com.adamthorpe.javacompiler.Types.ConstantPool;

import com.adamthorpe.javacompiler.ByteConvert;

public abstract class CONSTANT_ref extends CONSTANT {

  protected byte[] class_index; //u2
  protected byte[] name_and_type_index; //u2

  public CONSTANT_ref(int tag, int class_index_pos, int name_and_type_index_pos) {
    super(tag);
    this.class_index = ByteConvert.intToBytes(2, class_index_pos);
    this.name_and_type_index = ByteConvert.intToBytes(2, name_and_type_index_pos);
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(tag, class_index, name_and_type_index);
  }

  @Override
  public int getLength() {
    return super.getLength() +
      class_index.length +
      name_and_type_index.length;
  }
}