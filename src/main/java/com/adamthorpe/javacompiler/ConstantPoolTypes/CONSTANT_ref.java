package com.adamthorpe.javacompiler.ConstantPoolTypes;

import com.adamthorpe.javacompiler.ByteConvert;

public abstract class CONSTANT_ref extends CONSTANT {

  protected byte[] class_index; //u2
  protected byte[] name_and_type_index; //u2

  public CONSTANT_ref(int class_index_pos, int name_and_type_index_pos) {
    this.class_index = ByteConvert.intToBytes(2, class_index_pos);
    this.name_and_type_index = ByteConvert.intToBytes(2, name_and_type_index_pos);
  }

  @Override
  public byte[] getData() {
    byte[] data = new byte[5]; 

    System.arraycopy(super.getData(), 0, data, 0, 1);
    System.arraycopy(class_index, 0, data, 1, 2);
    System.arraycopy(name_and_type_index, 0, data, 3, 2);

    return data;
  }

}
