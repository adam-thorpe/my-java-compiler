package com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries;

/**
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.2</p>
 */
public class CONSTANT_Fieldref_info extends CONSTANT_ref {
  
  public CONSTANT_Fieldref_info(int class_index_pos, int name_and_type_index_pos) {
    super(9, class_index_pos, name_and_type_index_pos);
  }
}