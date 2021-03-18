package com.adamthorpe.javacompiler.ClassFile.LocalVariables;

import com.adamthorpe.javacompiler.Compiler.Type;

/**
 * <p>Stores data about a local variable.</p>
 */
public class LocalVariable {
  
  protected String name;
  protected Type type;

  public LocalVariable(String name, Type type) {
    this.name=name;
    this.type=type;
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
}
