package com.adamthorpe.javacompiler.Types.Code;

import com.adamthorpe.javacompiler.Types.Tables.ConstantPool;
import com.adamthorpe.javacompiler.Types.Tables.DataTable;

public class ByteCode extends DataTable<Instruction> {

  private static final long serialVersionUID = 1L;
  private ConstantPool constantPool;
  private int max_stack;
  private int max_locals;

  public ByteCode(ConstantPool constantPool) {
    this.constantPool = constantPool;

    max_stack=1;
    max_locals=1;
  }  

  public void addInstruction(OpCode op) {
    this.add(new Instruction(op));
  }

  public void addInstruction(OpCode op, int bytes, int cpIndex) {
    this.add(new Instruction(op, bytes, cpIndex));
  }

  public void addMaxStack(int increment) {
    max_stack+=increment;
  }

  public int getMaxStack() {
    return max_stack;
  }

  public void addMaxLocals(int increment) {
    max_locals+=increment;
  }

  public int getMaxLocals() {
    return max_locals;
  }
}