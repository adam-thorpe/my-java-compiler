package com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries;

import com.adamthorpe.javacompiler.Utilities.ByteConvert;

public class CONSTANT_Utf8_info extends CONSTANT {

  protected byte[] length; //u2
  protected byte[] bytes; //length

  public CONSTANT_Utf8_info(String data) {
    super(1);
    this.bytes = data.getBytes();
    this.length = ByteConvert.intToBytes(2, bytes.length);
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(tag, length, bytes);
  }

  @Override
  public int getLength() {
    return super.getLength() +
      length.length +
      bytes.length;
  }
}