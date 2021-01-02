package com.adamthorpe.javacompiler.Types.Tables;

import com.adamthorpe.javacompiler.Types.ConstantPool.CONSTANT;
import com.adamthorpe.javacompiler.Types.ConstantPool.CONSTANT_Class_info;
import com.adamthorpe.javacompiler.Types.ConstantPool.CONSTANT_Methodref_info;
import com.adamthorpe.javacompiler.Types.ConstantPool.CONSTANT_NameAndType_info;
import com.adamthorpe.javacompiler.Types.ConstantPool.CONSTANT_Utf8_info;

public class ConstantPool extends DataTable<CONSTANT> {
  static final long serialVersionUID = 1L;

  public ConstantPool() {
    super();
  }

  public int addUtf8_info(String data) {
    return insert(new CONSTANT_Utf8_info(data));
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
}
