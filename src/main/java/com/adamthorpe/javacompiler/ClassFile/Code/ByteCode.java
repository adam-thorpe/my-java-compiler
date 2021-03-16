package com.adamthorpe.javacompiler.ClassFile.Code;

import com.adamthorpe.javacompiler.ClassFile.DataTable;
import com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable.StackMapEntries;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.ConstantPool;

public class ByteCode extends DataTable<Instruction> {

  private static final long serialVersionUID = 1L;
  private ConstantPool constantPool;
  private int max_stack;
  private int max_locals;
  private int currentIndex;
  private StackMapEntries stackMapEntries;

  public ByteCode(ConstantPool constantPool, StackMapEntries stackMapEntries) {
    this.constantPool = constantPool;
    this.stackMapEntries = stackMapEntries;

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

  public void addJumpInstruction(OpCode op, Instruction jumpTo) {
    JumpInstruction i = new JumpInstruction(op, currentIndex, jumpTo);
    this.add(i);
    stackMapEntries.insertInstruction(i);
    currentIndex+=op.getLen();
  }

  public void addJumpInstruction(OpCode op, Instruction jumpTo, int jumpToIndexOffset) {
    JumpInstruction i = new JumpInstruction(op, currentIndex, jumpTo, jumpToIndexOffset);
    this.add(i);
    stackMapEntries.insertInstruction(i);
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