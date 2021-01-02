package com.adamthorpe.javacompiler.Types.ConstantPool;

import com.adamthorpe.javacompiler.ByteConvert;

public class CONSTANT_Utf8_info extends CONSTANT {

  protected byte[] length; //u2
  protected int lengthInt;
  protected byte[] bytes; //length

  public CONSTANT_Utf8_info(String data) {
    this.tag = (byte) 1;

    this.bytes = data.getBytes();
    this.lengthInt = bytes.length;
    System.out.println(data);
    this.length = ByteConvert.intToBytes(2, lengthInt);
  }

  @Override
  public byte[] getData() {
    byte[] data = new byte[3+lengthInt];
    
    System.arraycopy(super.getData(), 0, data, 0, 1);
    System.arraycopy(length, 0, data, 1, 2);
    System.arraycopy(bytes, 0, data, 3, lengthInt);

    return data;
  }

  @Override
  public int getLength() {
    return super.getLength() + 2 + lengthInt;
  }

}
