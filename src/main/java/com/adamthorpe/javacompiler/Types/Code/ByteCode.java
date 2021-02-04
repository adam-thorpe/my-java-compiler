package com.adamthorpe.javacompiler.Types.Code;

import com.adamthorpe.javacompiler.Types.Tables.ConstantPool;
import com.adamthorpe.javacompiler.Types.Tables.DataTable;

public class ByteCode extends DataTable<Instruction> {

  private static final long serialVersionUID = 1L;
  private ConstantPool constantPool;

  public ByteCode(ConstantPool constantPool) {
    this.constantPool = constantPool;
  }  

  public void addInstruction(OpCode op) {
    this.add(new Instruction(op));
  }

  public void addInstruction(OpCode op, int bytes, int cpIndex) {
    this.add(new Instruction(op, bytes, cpIndex));
  }
}