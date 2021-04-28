package com.adamthorpe.javacompiler.Compiler;

import com.adamthorpe.javacompiler.ClassFile.Code.JumpInstruction;

public class EvaluatedData {

  protected Type type;
  protected Object data;
  protected JumpInstruction jumpInstruction;

  public EvaluatedData(Type type, Object data, JumpInstruction jumpInstruction) {
    this.type=type;
    this.data=data;
    this.jumpInstruction=jumpInstruction;
  }

  public EvaluatedData(Type type, Object data) {
    this(type, data, null);
  }

  public EvaluatedData(Type type, JumpInstruction jumpInstruction) {
    this(type, null, jumpInstruction);
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
    return jumpInstruction!=null;
  }

  public JumpInstruction getInstruction() {
    return jumpInstruction;
  }
  
}