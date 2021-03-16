package com.adamthorpe.javacompiler.ClassFile.Attributes.Code;

import com.adamthorpe.javacompiler.ClassFile.ByteData;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

public class Exception_table implements ByteData {
  byte[] start_pc; //u2
  byte[] end_pc; //u2
  byte[] handler_pc; //u2
  byte[] catch_type; //u2

  public Exception_table() {
  }

  @Override
  public int getLength() {
    return 8;
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(start_pc, end_pc, handler_pc, catch_type);
  }
}
