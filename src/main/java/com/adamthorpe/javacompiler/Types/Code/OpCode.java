package com.adamthorpe.javacompiler.Types.Code;

public enum OpCode {
  iconst_m1 (2, 1),
  iconst_0 (3, 1),
  iconst_1 (4, 1),
  iconst_2 (5, 1),
  iconst_3 (6, 1),
  iconst_4 (7, 1),
  iconst_5 (8, 1),
  ldc (18, 3),
  iload_0 (26, 1),
  iload_1 (27, 1),
  iload_2 (28, 1),
  iload_3 (29, 1),
  aload_0 (42, 1),
  istore (54, 3),
  istore_0 (59, 1),
  istore_1 (60, 1),
  istore_2 (61, 1),
  istore_3 (62, 1),
  iadd (96, 1),
  isub (100, 1),
  ifeq (153, 5),
  ifne (154, 5),
  iflt (155, 5),
  ifge (156, 5),
  ifgt (157, 5),
  ifle (158, 5),
  goto_ (167, 5),
  ireturn (172, 1),
  areturn (176, 1),
  return_ (177, 1),
  getstatic (178, 5),
  invokevirtual (182, 5),
  invokespecial(183, 5);

  private final int opCode;
  private final int len;
   
  private OpCode(int opCode, int len) {
    this.opCode = opCode;
    this.len = len;
  }

  public int getCode() {
    return opCode;
  }

  public int getLen() {
    return len;
  }
}
