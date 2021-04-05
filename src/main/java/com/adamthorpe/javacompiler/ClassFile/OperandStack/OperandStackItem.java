package com.adamthorpe.javacompiler.ClassFile.OperandStack;

import com.adamthorpe.javacompiler.Compiler.Type;

public class OperandStackItem {
  
  protected Type operandType;
  protected int index;

  public OperandStackItem(Type operandType, int index) {
    this.operandType=operandType;
    this.index=index;
  }

  public Type getType() {
    return operandType;
  }

  public int getIndex() {
    return index;
  }
}