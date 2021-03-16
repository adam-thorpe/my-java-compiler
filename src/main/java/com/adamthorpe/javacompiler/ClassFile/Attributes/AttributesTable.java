package com.adamthorpe.javacompiler.ClassFile.Attributes;

import com.adamthorpe.javacompiler.ClassFile.DataTable;
import com.adamthorpe.javacompiler.ClassFile.Attributes.Code.Code_attribute;
import com.adamthorpe.javacompiler.ClassFile.Attributes.SourceFile.SourceFile_attribute;
import com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable.StackMapEntries;
import com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable.StackMapTable_attribute;
import com.adamthorpe.javacompiler.ClassFile.Code.ByteCode;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.ConstantPool;

public class AttributesTable extends DataTable<Attributes_info> {

  private static final long serialVersionUID = 1L;
  private ConstantPool constantPool;
  
  public AttributesTable(ConstantPool constantPool) {
    super();
    this.constantPool = constantPool;
  }

  public void addCodeAttribute(ByteCode code, AttributesTable attributes) {
    int attribute_name_index = constantPool.addUtf8_info("Code");
    insert(new Code_attribute(attribute_name_index, code, attributes));
  }

  public void addSourceFileAttribute(String sourcefile) {
    int attribute_name_index = constantPool.addUtf8_info("SourceFile");
    int sourcefile_index = constantPool.addUtf8_info(sourcefile);
    insert(new SourceFile_attribute(attribute_name_index, sourcefile_index));
  }

  public void addStackMapTableAttribute(StackMapEntries entries) {
    int attribute_name_index = constantPool.addUtf8_info("StackMapTable");
    insert(new StackMapTable_attribute(attribute_name_index, entries));
  }
}