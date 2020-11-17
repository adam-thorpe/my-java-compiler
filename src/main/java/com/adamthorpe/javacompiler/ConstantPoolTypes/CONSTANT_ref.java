package com.adamthorpe.javacompiler.ConstantPoolTypes;

public abstract class CONSTANT_ref extends CONSTANT {

  protected byte[] class_index = new byte[2]; //u2
  protected byte[] name_and_type_index = new byte[2]; //u2

  @Override
  public byte[] getData() {
    byte[] data = new byte[5]; 

    System.arraycopy(super.getData(), 0, data, 0, 1);
    System.arraycopy(class_index, 0, data, 1, 2);
    System.arraycopy(name_and_type_index, 0, data, 3, 2);

    return data;
  }

}
