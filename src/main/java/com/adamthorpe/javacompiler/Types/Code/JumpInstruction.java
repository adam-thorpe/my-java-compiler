package com.adamthorpe.javacompiler.Types.Code;

import com.adamthorpe.javacompiler.ByteConvert;

public class JumpInstruction extends Instruction {

  protected Instruction jumpTo;
  protected int offset;

  public JumpInstruction(OpCode op, int index, Instruction jumpTo) {
    super(op, index);
    this.args = new byte[2];
    this.jumpTo = jumpTo;
    this.offset=0;
  }

  public JumpInstruction(OpCode op, int index, Instruction jumpTo, int offset) {
    this(op, index, jumpTo);
    this.offset=offset;
  }

  public int calculateJump() {
    return jumpTo.getIndex()-index+offset;
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(opcode, ByteConvert.intToBytes(2, calculateJump()));
  }
  
}