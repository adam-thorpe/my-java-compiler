package com.adamthorpe.javacompiler.Types.Tables;

import java.util.ArrayList;
import java.util.Arrays;

import com.adamthorpe.javacompiler.Types.ByteData;

public abstract class DataTable<E extends ByteData> extends ArrayList<E> implements ByteData {

  private static final long serialVersionUID = 1L;

  public DataTable() {
    super();
  }

  /**
   * Adds a new element to the table, if it does not already exist
   * @param e Element to be inserted
   * @return Index of the element
   */
  protected int insert(E e) {
    int index = find(e);

    if (index != -1) {
      return index;
    } else {
      add(e);
      return this.size()-1;
    }
  }

  /**
   * Finds an element in the table
   * @param e The element
   * @return Index of the element. -1 if element cannot be found
   */
  public int find(E e) {
    int index = -1;

    for(int i=0; i<this.size(); i++) {
      if(Arrays.equals(this.get(i).getData(), e.getData())) {
        index=i;
        break;
      }
    }
    return index;
  }

  /**
   * Sums the length of each element in the table
   * @return Length of byte data
   */
  public int getLength() {
    int length = 0;
    for (E e : this) {
      length += e.getLength();
    }

    return length;
  }

  /**
   * Gets the data of the element in the table
   * @return Byte array of data
   */
  public byte[] getData() {
    byte[] data = new byte[getLength()];
    int counter = 0;

    for (E e : this) {
      System.arraycopy(e.getData(), 0, data, counter, e.getLength());
      counter+=e.getLength();
    }

    return data;
  }
  
}
