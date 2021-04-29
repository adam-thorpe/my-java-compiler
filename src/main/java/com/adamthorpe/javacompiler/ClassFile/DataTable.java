package com.adamthorpe.javacompiler.ClassFile;

import java.util.ArrayList;

import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>This class adds implementations for all array-based containers of ClassFile data.</p>
 */
public abstract class DataTable<E extends ByteData> extends ArrayList<E> implements ByteData {

  protected static final long serialVersionUID = 1L;

  /**
   * <p>Sums the length of each element in the table.</p>
   * 
   * @return  Length of byte data
   */
  public int getLength() {
    int length = 0;
    for (E e : this) {
      length += e.getLength();
    }

    return length;
  }

  /**
   * <p>Creates one long array of data from each entry in the table.</p>
   * 
   * @return  Byte array of data
   */
  public byte[] getData() {
    return ByteConvert.copyBytes(this);
  }
}