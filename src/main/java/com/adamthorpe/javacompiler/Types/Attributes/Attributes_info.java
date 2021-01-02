package com.adamthorpe.javacompiler.Types.Attributes;

import com.adamthorpe.javacompiler.Types.ByteData;

public abstract class Attributes_info implements ByteData {

  protected byte[] attribute_name_index; //u2
  protected byte[] attribute_length; //u4
  protected int attribute_length_int;

  @Override
  public byte[] getData() {
    byte[] data = new byte[6+attribute_length_int];
    
    System.arraycopy(attribute_name_index, 0, data, 0, 2);
    System.arraycopy(attribute_length, 0, data, 2, 4);

    return data;
  }

  @Override
  public int getLength() {
    return attribute_name_index.length +
      attribute_length.length;
  }
}
