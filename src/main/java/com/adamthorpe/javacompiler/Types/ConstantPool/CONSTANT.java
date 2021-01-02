package com.adamthorpe.javacompiler.Types.ConstantPool;

import com.adamthorpe.javacompiler.Types.ByteData;

public abstract class CONSTANT implements ByteData {

  protected byte tag; //u1

  public byte[] getData() {
    byte[] data = new byte[1];
    data[0] = tag;

    return data;
  }

  public int getLength() {
    return 1;
  }
}
