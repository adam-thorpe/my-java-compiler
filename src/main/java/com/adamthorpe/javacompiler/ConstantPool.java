package com.adamthorpe.javacompiler;

import java.util.ArrayList;

import com.adamthorpe.javacompiler.ConstantPoolTypes.CONSTANT;
import com.adamthorpe.javacompiler.ConstantPoolTypes.CONSTANT_Class_info;
import com.adamthorpe.javacompiler.ConstantPoolTypes.CONSTANT_Methodref_info;
import com.adamthorpe.javacompiler.ConstantPoolTypes.CONSTANT_NameAndType_info;
import com.adamthorpe.javacompiler.ConstantPoolTypes.CONSTANT_Utf8_info;

public class ConstantPool extends ArrayList<CONSTANT> {
  static final long serialVersionUID = 641046281;

  public ConstantPool() {
    super();
  }

  public int insert(CONSTANT data) {
    int index = find(data);

    if (index != -1) {
      return index;
    } else {
      this.add(data);
      return this.size()-1;
    }
  }

  public int addClass_info(String name) {
    int name_index = insert(new CONSTANT_Utf8_info(name));
    return insert(new CONSTANT_Class_info(name_index));
  }

  public int addNameAndType_info(String name, String descriptor) {
    int name_index = insert(new CONSTANT_Utf8_info(name));
    int descriptor_index = insert(new CONSTANT_Utf8_info(descriptor));
    return insert(new CONSTANT_NameAndType_info(name_index, descriptor_index));
  }

  public int addMethod_info(String className, String methodName, String descriptor) {
    int class_index = addClass_info(className);
    int name_and_type_index = addNameAndType_info(methodName, descriptor);
    return insert(new CONSTANT_Methodref_info(class_index, name_and_type_index));
  }

  public int find(CONSTANT data) {
    int index = -1;

    for(int i=0; i<this.size(); i++) {
      if(this.get(i).getData() == data.getData()) {
        index=i;
        break;
      }
    }

    return index;
  }
}
