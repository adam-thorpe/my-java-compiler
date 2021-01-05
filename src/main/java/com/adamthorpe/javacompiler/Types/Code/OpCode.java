package com.adamthorpe.javacompiler.Types.Code;

public enum OpCode {
  aload_0 (42),
  return_ (177),
  invokespecial(183);

  private final int opCode;

  private OpCode(int opCode) {
    this.opCode = opCode;
  }

  public int getCode() {
    return opCode;
  }
}
