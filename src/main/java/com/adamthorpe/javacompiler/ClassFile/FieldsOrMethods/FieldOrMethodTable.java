package com.adamthorpe.javacompiler.ClassFile.FieldsOrMethods;

import com.adamthorpe.javacompiler.ClassFile.DataTable;
import com.adamthorpe.javacompiler.ClassFile.Attributes.AttributesTable;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.ConstantPool;

public class FieldOrMethodTable extends DataTable<Field_or_Method_info> {

  private static final long serialVersionUID = 1L;
  private ConstantPool constantPool;

  public FieldOrMethodTable(ConstantPool constantPool) {
    super();
    this.constantPool = constantPool;
  }

  /**
   * <p>Insert a field or method description into the table.</p>
   * 
   * @param name        Name of the field or method
   * @param descriptor  Descriptor of the field or method
   * @param attributes  An array of attributes
   * @return            Index of the element in the table
   */
  public void insert(String name, int accessFlags, String descriptor, AttributesTable attributes) {
    int name_index = constantPool.addUtf8_info(name);
    int descriptor_index = constantPool.addUtf8_info(descriptor);

    add(new Field_or_Method_info(name_index, accessFlags, descriptor_index, attributes));
  }
}