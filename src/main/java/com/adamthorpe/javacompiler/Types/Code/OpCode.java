package com.adamthorpe.javacompiler.Types.Code;

public enum OpCode {
  ldc (18), // Load constant
  iload_1 (27),
  aload_0 (42),
  ireturn (172),
  areturn (176),
  return_ (177),
  getstatic (178),
  invokevirtual (182),
  invokespecial(183);

  private final int opCode;
   
  private OpCode(int opCode) {
    this.opCode = opCode;
  }

  public int getCode() {
    return opCode;
  }
}
