package com.adamthorpe.javacompiler.ClassFile.LocalVariables;

import com.adamthorpe.javacompiler.Compiler.Type;

/**
 * <p>Stores data about a local variable.</p>
 */
public class LocalVariable {
  
  protected String name;
  protected Type type;
  protected int index;

  public LocalVariable(String name, Type type, int index) {
    this.name=name;
    this.type=type;
    this.index=index;
  }

  /**
   * <p>Get the name of the variable.</p>
   * 
   * @return  <code>name</code> of the variable
   */
  public String getName() {
    return name;
  }

  /**
   * <p>Get the type of the variable.</p>
   * 
   * @return  <code>type</code> of the variable
   */
  public Type getType() {
    return type;
  }

  /**
   * <p>Get the index of where the variable was declared.</p>
   * 
   * @return index
   */
  public int getIndex() {
    return index;
  }
}
