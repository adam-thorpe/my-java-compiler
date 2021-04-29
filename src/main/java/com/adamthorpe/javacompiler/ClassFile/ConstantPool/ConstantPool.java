package com.adamthorpe.javacompiler.ClassFile.ConstantPool;

import java.util.Arrays;

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

  /**
   * <p>Adds a new element to the constant pool, if it does not already exist.</p>
   * 
   * @param e Element to be inserted
   * @return  Index of the newly inserted element
   */
  protected int insert(CONSTANT e) {
    int index = find(e);

    if (index != -1) {
      return index;
    } else {
      add(e);
      return this.size()-1;
    }
  }

  /**
   * <p>Finds an element in the constant pool.</p>
   * 
   * @param e The element
   * @return  Index of the element. -1 if the  element cannot be found
   */
  public int find(CONSTANT e) {
    int index = -1;

    for(int i=0; i<this.size(); i++) {
      if(Arrays.equals(this.get(i).getData(), e.getData())) {
        index=i;
        break;
      }
    }
    return index;
  }

  /**
   * <p>Adds a raw utf8_info entry to the constant pool.</p>
   * 
   * @param data  A string of data
   * @return      Index of the entry
   */
  public int addUtf8_info(String data) {
    return insert(new CONSTANT_Utf8_info(data));
  }

  /**
   * <p>Adds a string_info entry to the constant pool.</p>
   * 
   * @param data  A string of data
   * @return      Index of the entry
   */
  public int addString_info(String data) {
    int string_index = addUtf8_info(data);
    return insert(new CONSTANT_String_info(string_index));
  }

  /**
   * <p>Adds a class_info entry to the constant pool.</p>
   * 
   * @param name  The class name
   * @return      Index of the entry
   */
  public int addClass_info(String name) {
    int name_index = addUtf8_info(name);
    return insert(new CONSTANT_Class_info(name_index));
  }

  /**
   * <p>Adds a name_and_type_info entry to the constant pool.</p>
   * 
   * @param name        Name of the type
   * @param descriptor  Descriptor of the type
   * @return            Index of the entry
   */
  public int addNameAndType_info(String name, String descriptor) {
    int name_index = addUtf8_info(name);
    int descriptor_index = addUtf8_info(descriptor);
    return insert(new CONSTANT_NameAndType_info(name_index, descriptor_index));
  }

  /**
   * <p>Adds a method_info entry to the constant pool.</p>
   * 
   * @param className   Name of the class
   * @param methodName  Name of the method
   * @param descriptor  Descriptor of the method
   * @return            Index of the entry
   */
  public int addMethod_info(String className, String methodName, String descriptor) {
    int class_index = addClass_info(className);
    int name_and_type_index = addNameAndType_info(methodName, descriptor);
    return insert(new CONSTANT_Methodref_info(class_index, name_and_type_index));
  }

  /**
   * <p>Adds a field_info entry to the constant pool.</p>
   * 
   * @param className   Name of the class
   * @param fieldName   Name of the field
   * @param descriptor  Descriptot of the field
   * @return            Index of the entry
   */
  public int addField_info(String className, String fieldName, String descriptor) {
    int class_index = addClass_info(className);
    int name_and_type_index = addNameAndType_info(fieldName, descriptor);
    return insert(new CONSTANT_Fieldref_info(class_index, name_and_type_index));
  }

  /**
   * <p>Sums the length of each element in the table. Ignores the entry at index 0.</p>
   * 
   * @return  Length of byte data
   */
  @Override
  public int getLength() {
    return super.getLength() -
      this.get(0).getLength();
  }

  /**
   * <p>Creates one long array of data from each entry in the table. Ignores entry at index 0.</p>
   * 
   * @return  Byte array of data
   */
  @Override
  public byte[] getData() {
    return ByteConvert.copyBytes(this.subList(1, this.size()));
  }
}