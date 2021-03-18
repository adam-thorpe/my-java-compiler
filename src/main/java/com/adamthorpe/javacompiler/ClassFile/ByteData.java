package com.adamthorpe.javacompiler.ClassFile;

/**
 * <p>An interface for all ClassFile related data.</p>
 */
public interface ByteData {
  
  /**
   * <p>Gets the length of the data.</p>
   * 
   * @return  Length of the data
   */
  public int getLength();
  
  /**
   * <p>Gets a byte array of data.</p>
   * 
   * @return  Byte array of data
   */
  public byte[] getData();
}