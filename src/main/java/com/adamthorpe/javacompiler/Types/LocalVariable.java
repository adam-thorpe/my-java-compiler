package com.adamthorpe.javacompiler.Types;

public class LocalVariable {
  
  private String name;
  private String type;

  public LocalVariable(String name, String type) {
    this.name=name;
    this.type=type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }
}
