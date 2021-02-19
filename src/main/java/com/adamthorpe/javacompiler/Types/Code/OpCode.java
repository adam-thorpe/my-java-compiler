package com.adamthorpe.javacompiler.Types.Code;

public enum OpCode {
  iconst_0 (3),
  iconst_1 (4),
  iconst_2 (5),
  iconst_3 (6),
  iconst_4 (7),
  iconst_5 (8),
  ldc (18),
  iload (21),
  iload_0 (26),
  iload_1 (27),
  iload_2 (28),
  iload_3 (29),
  aload_0 (42),
  istore (54),
  istore_0 (59),
  istore_1 (60),
  istore_2 (61),
  istore_3 (62),
  iadd (96),
  isub (100),
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
