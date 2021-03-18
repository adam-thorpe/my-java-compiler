package com.adamthorpe.javacompiler.ClassFile.Code;

import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>Describes a single OpCode Instruction, with any specified arguments. This Instruction is a Jump Instruction
 * and contains a link to a future instruction, to which it points to.</p>
 * 
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html</p>
 */
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

  /**
   * <p>Calculates the index of the instruction to jump to.</p>
   * 
   * @return  Jump index
   */
  public int calculateJump() {
    int jump = jumpTo.getIndex()-index+offset;
    args = ByteConvert.intToBytes(2, jump);
    
    return jump;
  }

  @Override
  public byte[] getData() {
    calculateJump();
    return ByteConvert.toByteArr(opcode, args);
  }
}