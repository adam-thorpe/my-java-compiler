package com.adamthorpe.javacompiler.Types.Attributes;

public class Exception_table {
  byte[] start_pc; //u2
  byte[] end_pc; //u2
  byte[] handler_pc; //u2
  byte[] catch_type; //u2

  public Exception_table() {

  }

  public byte[] getData() {
    byte[] data = new byte[8];
    
    System.arraycopy(start_pc, 0, data, 0, 2);
    System.arraycopy(end_pc, 0, data, 2, 2);
    System.arraycopy(handler_pc, 0, data, 4, 2);
    System.arraycopy(catch_type, 0, data, 6, 2);

    return data;
  }
  
}
