package com.adamthorpe.javacompiler.ClassFile.LocalVariables;

import java.util.ArrayList;

public class LocalVariableTable extends ArrayList<LocalVariable> {

  private static final long serialVersionUID = 1L;
  
  /**
   * <p>Gets the index of a local variable in this table. Returns -1 if it cannot find it.</p>
   * 
   * @param name the name of the local variable
   * @return index
   */
  public int find(String name) {
    
    int x=0;
    while (x < this.size()) {
      if (this.get(x).getName().equals(name)) {
        return x;
      }
      x=x+1;
    }

    return -1;
  }
}
