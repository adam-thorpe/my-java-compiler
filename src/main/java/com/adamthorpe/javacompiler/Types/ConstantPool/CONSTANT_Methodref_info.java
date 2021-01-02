package com.adamthorpe.javacompiler.Types.ConstantPool;

public class CONSTANT_Methodref_info extends CONSTANT_ref {

  public CONSTANT_Methodref_info(int class_index_pos, int name_and_type_index_pos) {
    super(class_index_pos, name_and_type_index_pos);
    this.tag = (byte) 10;
  }
}
