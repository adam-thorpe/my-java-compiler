package com.adamthorpe.javacompiler.ClassFile.ConstantPool;

import com.adamthorpe.javacompiler.ClassFile.DataTable;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries.CONSTANT;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries.CONSTANT_Class_info;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries.CONSTANT_DUMMY;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries.CONSTANT_Fieldref_info;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries.CONSTANT_Methodref_info;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries.CONSTANT_NameAndType_info;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries.CONSTANT_String_info;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.Entries.CONSTANT_Utf8_info;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

public class ConstantPool extends DataTable<CONSTANT> {
  static final long serialVersionUID = 1L;

  public ConstantPool() {
    super();
    add(new CONSTANT_DUMMY());
  }

  public int addUtf8_info(String data) {
    return insert(new CONSTANT_Utf8_info(data));
  }

  public int addString_info(String data) {
    int string_index = addUtf8_info(data);
    return insert(new CONSTANT_String_info(string_index));
  }

  public int addClass_info(String name) {
    int name_index = addUtf8_info(name);
    return insert(new CONSTANT_Class_info(name_index));
  }

  public int addNameAndType_info(String name, String descriptor) {
    int name_index = addUtf8_info(name);
    int descriptor_index = addUtf8_info(descriptor);
    return insert(new CONSTANT_NameAndType_info(name_index, descriptor_index));
  }

  public int addMethod_info(String className, String methodName, String descriptor) {
    int class_index = addClass_info(className);
    int name_and_type_index = addNameAndType_info(methodName, descriptor);
    return insert(new CONSTANT_Methodref_info(class_index, name_and_type_index));
  }

  public int addField_info(String className, String fieldName, String descriptor) {
    int class_index = addClass_info(className);
    int name_and_type_index = addNameAndType_info(fieldName, descriptor);
    return insert(new CONSTANT_Fieldref_info(class_index, name_and_type_index));
  }

  /**
   * Gets the data of the element in the constant pool. Ignores entry at index 0
   * @return Byte array of data
   */
  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(this.subList(1, this.size()));
  }

  /**
   * Sums the length of each element in the table, excluding the first element
   * @return Length of byte data
   */
  @Override
  public int getLength() {
    return super.getLength() -
      this.get(0).getLength();
  }
}
