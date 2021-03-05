package com.adamthorpe.javacompiler.Types.Code;

import com.adamthorpe.javacompiler.ByteConvert;

public class JumpInstruction extends Instruction {

  protected Instruction jumpTo;

  public JumpInstruction(OpCode op, int index, Instruction jumpTo) {
    super(op, index);
    this.args = new byte[2];
    this.jumpTo = jumpTo;
  }

  @Override
  public byte[] getData() {
    args = ByteConvert.intToBytes(2, jumpTo.getIndex()-index);
    return ByteConvert.toByteArr(opcode, args);
  }
  
}