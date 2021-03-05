package com.adamthorpe.javacompiler.Types.Code;

import com.adamthorpe.javacompiler.Types.Tables.ConstantPool;
import com.adamthorpe.javacompiler.Types.Tables.DataTable;

public class ByteCode extends DataTable<Instruction> {

  private static final long serialVersionUID = 1L;
  private ConstantPool constantPool;
  private int max_stack;
  private int max_locals;
  private int currentIndex;

  public ByteCode(ConstantPool constantPool) {
    this.constantPool = constantPool;

    max_stack=1;
    max_locals=1;

    currentIndex=0;
  }  

  public void addInstruction(Instruction instruction) {
    instruction.setIndex(currentIndex);
    this.add(instruction);
    currentIndex+=instruction.getOpCode().getLen();
  }

  public void addInstruction(OpCode op) {
    this.add(new Instruction(op, currentIndex));
    currentIndex+=op.getLen();
  }

  public void addInstruction(OpCode op, int bytes, int cpIndex) {
    this.add(new Instruction(op, currentIndex, bytes, cpIndex));
    currentIndex+=op.getLen();
  }

  public void addJumpInstruction(OpCode op, int jumpToIndexOffset) {
    this.add(new Instruction(op, currentIndex, 2, op.getLen()+jumpToIndexOffset));
    currentIndex+=op.getLen();
  }

  public void addJumpInstruction(OpCode op, Instruction jumpTo) {
    this.add(new JumpInstruction(op, currentIndex, jumpTo));
    currentIndex+=op.getLen();
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

  public int getCurrentIndex() {
    return currentIndex;
  }
}