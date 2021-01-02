package com.adamthorpe.javacompiler.Types;

public interface ByteData {
  
  /**
   * 
   * @return Length of byte data
   */
  public int getLength();
  
  /**
   * 
   * @return Byte array of data
   */
  public byte[] getData();
}