package com.adamthorpe.javacompiler.Types.Tables;

import com.adamthorpe.javacompiler.Types.Field_or_Method_info;

public class FieldOrMethodTable extends DataTable<Field_or_Method_info> {

  private static final long serialVersionUID = 1L;
  private ConstantPool constantPool;

  public FieldOrMethodTable(ConstantPool constantPool) {
    super();
    this.constantPool = constantPool;
  }

  /**
   * Insert a field or method description into the table
   * @param name The name of the field or method
   * @param descriptor Description of the field or method
   * @param attributes An array of attributes
   * @return Index of the element in the table
   */
  public int insert(String name, int accessFlags, String descriptor, AttributesTable attributes) {
    int name_index = constantPool.addUtf8_info(name);
    int descriptor_index = constantPool.addUtf8_info(descriptor);

    return insert(new Field_or_Method_info(name_index, accessFlags, descriptor_index, attributes));
  }
}