package com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries;

public class CONSTANT_Methodref_info extends CONSTANT_ref {

  public CONSTANT_Methodref_info(int class_index_pos, int name_and_type_index_pos) {
    super(10, class_index_pos, name_and_type_index_pos);
  }
}