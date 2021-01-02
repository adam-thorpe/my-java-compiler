package com.adamthorpe.javacompiler.Types;

import com.adamthorpe.javacompiler.ByteConvert;

public class Interface_info implements ByteData {

  private byte[] name_index; //u2

  public Interface_info(int name_index) {
    this.name_index = ByteConvert.intToBytes(2, name_index);
  }

  @Override
  public int getLength() {
    return 2;
  }

  @Override
  public byte[] getData() {
    return name_index;
  }
}
