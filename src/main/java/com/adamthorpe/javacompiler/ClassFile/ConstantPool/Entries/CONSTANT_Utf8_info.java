package com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries;

import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.7</p>
 */
public class CONSTANT_Utf8_info extends CONSTANT {

  protected byte[] length;  //u2
  protected byte[] bytes;   //length

  public CONSTANT_Utf8_info(String data) {
    super(1);
    this.bytes = data.getBytes();
    this.length = ByteConvert.intToBytes(2, bytes.length);
  }

  @Override
  public byte[] getData() {
    return ByteConvert.copyBytes(tag, length, bytes);
  }

  @Override
  public int getLength() {
    return super.getLength() +
      length.length +
      bytes.length;
  }
}