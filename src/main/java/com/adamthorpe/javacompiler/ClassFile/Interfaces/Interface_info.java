package com.adamthorpe.javacompiler.ClassFile.Interfaces;

import com.adamthorpe.javacompiler.ClassFile.ByteData;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

public class Interface_info implements ByteData {

  private byte[] name_index; //u2

  public Interface_info(int name_index) {
    this.name_index = ByteConvert.intToBytes(2, name_index);
  }

  @Override
  public int getLength() {
    return name_index.length;
  }

  @Override
  public byte[] getData() {
    return name_index;
  }
}
