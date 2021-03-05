package com.adamthorpe.javacompiler.Types.Code;

import com.adamthorpe.javacompiler.ByteConvert;
import com.adamthorpe.javacompiler.Types.ByteData;

public class Instruction implements ByteData {

  protected OpCode op;
  protected byte[] opcode; //u1
  protected byte[] args; //optional
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

  public void setIndex(int index) {
    this.index = index;
  }

  public OpCode getOpCode() {
    return op;
  }

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
}