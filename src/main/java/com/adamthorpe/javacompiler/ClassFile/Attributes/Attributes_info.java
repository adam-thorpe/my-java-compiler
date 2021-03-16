package com.adamthorpe.javacompiler.ClassFile.Attributes;

import com.adamthorpe.javacompiler.ClassFile.ByteData;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

public abstract class Attributes_info implements ByteData {

  protected byte[] attribute_name_index; //u2
  protected byte[] attribute_length; //u4

  public Attributes_info(int attribute_name_index) {
    this.attribute_name_index = ByteConvert.intToBytes(2, attribute_name_index);
  }

  protected void setAttributeLength(int attribute_length) {
    this.attribute_length = ByteConvert.intToBytes(4, attribute_length);
  }

  @Override
  public int getLength() {
    return 6;
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(attribute_name_index, attribute_length);
  }
}