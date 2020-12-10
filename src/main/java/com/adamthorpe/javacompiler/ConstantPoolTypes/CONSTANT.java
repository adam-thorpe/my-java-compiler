package com.adamthorpe.javacompiler.ConstantPoolTypes;

public abstract class CONSTANT {

  protected byte tag; //u1

  public byte getTag() {
    return tag;
  }

  public byte[] getData() {
    byte[] data = new byte[1];
    data[0] = tag;

    return data;
  }

  public int getLength() {
    return 1;
  }
}
