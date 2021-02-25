package com.adamthorpe.javacompiler.Types;

public class LocalVariable {
  
  private String name;
  private Type type;

  public LocalVariable(String name, Type type) {
    this.name=name;
    this.type=type;
  }

  public String getName() {
    return name;
  }

  public Type getType() {
    return type;
  }
}
