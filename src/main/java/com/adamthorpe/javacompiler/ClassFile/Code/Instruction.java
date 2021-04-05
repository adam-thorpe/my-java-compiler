package com.adamthorpe.javacompiler.ClassFile.Code;

import com.adamthorpe.javacompiler.ClassFile.ByteData;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>Describes a single OpCode Instruction, with any specified arguments.</p>
 * 
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-6.html</p>
 */
public class Instruction implements ByteData {

  protected byte[] opcode;  //u1
  protected byte[] args;    //optional

  protected OpCode op;
  protected int index;

  public Instruction(OpCode op, int index) {
    this.op=op;
    this.opcode = ByteConvert.intToBytes(1, op.getCode());
    this.args = new byte[0];
    this.index = index;
  }

  public Instruction(OpCode op, int index, int bytes, int args) {
    this(op, index);
    this.args = ByteConvert.intToBytes(bytes, args);
  }

  /**
   * <p>Sets the index of this instruction.</p>
   * 
   * @param index The given index
   */
  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * <p>Returns the OpCode of this instruction.</p>
   * 
   * @return  The OpCode of the instruction
   */
  public OpCode getOpCode() {
    return op;
  }

  /**
   * <p>Returns the index of this instruction in the instruction set.</p>
   * 
   * @return  The index
   */
  public int getIndex() {
    return index;
  }

  @Override
  public int getLength() {
    return opcode.length +
      args.length;
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(opcode, args);
  }

  /**
   * 
   * 
   * @return
   */
  public boolean isJumpInstruction() {
    return false;
  }

  /**
   * 
   * 
   * @return
   */
  public JumpInstruction toJumpInstruction() {
    return null;
  }
}