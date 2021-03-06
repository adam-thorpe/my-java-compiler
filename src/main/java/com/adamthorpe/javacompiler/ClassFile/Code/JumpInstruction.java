package com.adamthorpe.javacompiler.ClassFile.Code;

import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>Describes a single OpCode Instruction, with any specified arguments. This Instruction is a Jump Instruction
 * and contains a link to a future instruction, to which it points to.</p>
 * 
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html</p>
 */
public class JumpInstruction extends Instruction {

  protected int jumpToIndex;

  public JumpInstruction(OpCode op, int index) {
    super(op, index);
    this.args = new byte[2];
  }

  public JumpInstruction(OpCode op, int index, int jumpToIndex) {
    super(op, index);
    setOffset(jumpToIndex);
  }

  public void setOffset(int jumpToIndex) {
    this.jumpToIndex=jumpToIndex;
    this.args=ByteConvert.intToBytes(2, jumpToIndex-index);
  }

  public int getJumpToIndex() {
    return jumpToIndex;
  }

  @Override
  public boolean isJumpInstruction() {
    return true;
  }

  @Override
  public JumpInstruction toJumpInstruction() {
    return this;
  }
}