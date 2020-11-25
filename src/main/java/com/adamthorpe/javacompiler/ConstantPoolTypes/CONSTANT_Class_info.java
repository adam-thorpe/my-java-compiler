package com.adamthorpe.javacompiler.ConstantPoolTypes;

import com.adamthorpe.javacompiler.ByteConvert;

public class CONSTANT_Class_info extends CONSTANT {
  
  private byte[] name_index; //u2
  // private CONSTANT_Utf8_info name_index_ref;

  public CONSTANT_Class_info(int name_index_pos) {
    this.tag = (byte) 7;
    this.name_index = ByteConvert.intToBytes(2, name_index_pos);
  }

  // public CONSTANT_Class_info(String className) {
  //   this.tag = (byte) 7;
  //   this.name_index_ref = new CONSTANT_Utf8_info(className);
  // }

  @Override
  public byte[] getData() {
    byte[] data = new byte[3];
    
    System.arraycopy(super.getData(), 0, data, 0, 1);
    System.arraycopy(name_index, 0, data, 1, 2);

    return data;
  }

  // public void setNameIndex(int name_index_pos) {
  //   name_index = ByteConvert.intToBytes(2, name_index_pos);
  // }
}