package com.adamthorpe.javacompiler.Types.ConstantPool;

import com.adamthorpe.javacompiler.ByteConvert;
import com.adamthorpe.javacompiler.Types.ByteData;

public abstract class CONSTANT implements ByteData {

  protected byte[] tag; //u1

  public CONSTANT(int tag) {
    this.tag = ByteConvert.intToBytes(1, tag);
  }

  public byte[] getData() {
    return tag;
  }

  public int getLength() {
    return tag.length;
  }
}
