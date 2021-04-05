package com.adamthorpe.javacompiler.ClassFile.LocalVariables;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.ClassFile.Code.ByteCode;
import com.adamthorpe.javacompiler.Compiler.EmptyType;
import com.adamthorpe.javacompiler.Compiler.Type;

public class LocalVariableTable extends ArrayList<LocalVariable> {

  private static final long serialVersionUID = 1L;
  protected ByteCode code;

  public LocalVariableTable(ByteCode code) {
    super();
    this.code=code;
    this.add(new LocalVariable("this", new EmptyType(), -1));
  }

  public void add(String name, Type type, int index) {
    this.add(new LocalVariable(name, type, index));
    code.addMaxLocals(1);
  }
  
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

  /**
   * 
   * 
   * @param index
   * @return
   */
  public List<LocalVariable> getCurrentLocals(int index) {
    List<LocalVariable> currentLocals = new ArrayList<>();
    
    for(LocalVariable localVariable : this) {
      if (localVariable.getIndex()<=index && localVariable.getIndex()>=0) {
        currentLocals.add(localVariable);
      }
    }

    return currentLocals;
  }
}
