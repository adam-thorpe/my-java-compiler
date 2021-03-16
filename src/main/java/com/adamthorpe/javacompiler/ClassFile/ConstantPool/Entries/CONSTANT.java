package com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries;

import com.adamthorpe.javacompiler.ClassFile.ByteData;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

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
