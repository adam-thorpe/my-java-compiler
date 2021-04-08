package com.adamthorpe.javacompiler.Compiler;

import com.adamthorpe.javacompiler.ClassFile.Code.Instruction;

public class EvaluatedData {

  protected Type type;
  protected Object data;
  protected Instruction dummy;

  public EvaluatedData(Type type, Object data, Instruction dummy) {
    this.type=type;
    this.data=data;
    this.dummy=dummy;
  }

  public EvaluatedData(Type type, Object data) {
    this(type, data, null);
  }

  public EvaluatedData(Type type, Instruction dummy) {
    this(type, null, dummy);
  }

  public EvaluatedData(Type type) {
    this(type, null, null);
  }

  public Type getType() {
    return type;
  }

  public boolean hasData() {
    return data!=null;
  }

  public Object getData() {
    return data;
  }

  public boolean hasInstruction() {
    return dummy!=null;
  }

  public Instruction getInstruction() {
    return dummy;
  }
  
}