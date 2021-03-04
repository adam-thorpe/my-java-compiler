package com.adamthorpe.javacompiler.Types.Code;

import com.adamthorpe.javacompiler.ByteConvert;
import com.adamthorpe.javacompiler.Types.ByteData;

public class Instruction implements ByteData {

  private byte[] opcode; //u1
  private byte[] args; //optional
  private int index;

  public Instruction(OpCode op, int index) {
    this.opcode = ByteConvert.intToBytes(1, op.getCode());
    this.args = new byte[0];
    this.index = index;
  }

  public Instruction(OpCode op, int index, int bytes, int cpIndex) {
    this(op, index);

    byte[] arg = ByteConvert.intToBytes(bytes, cpIndex);
    this.args = arg;
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