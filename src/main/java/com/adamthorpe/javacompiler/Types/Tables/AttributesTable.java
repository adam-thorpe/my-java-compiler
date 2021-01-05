package com.adamthorpe.javacompiler.Types.Tables;

import com.adamthorpe.javacompiler.Types.Attributes.Attributes_info;
import com.adamthorpe.javacompiler.Types.Attributes.Code_attribute;
import com.adamthorpe.javacompiler.Types.Attributes.SourceFile_attribute;
import com.adamthorpe.javacompiler.Types.Code.ByteCode;

public class AttributesTable extends DataTable<Attributes_info> {

  private static final long serialVersionUID = 1L;
  private ConstantPool constantPool;
  
  public AttributesTable(ConstantPool constantPool) {
    super();
    this.constantPool = constantPool;
  }

  //WIP

  public void addCodeAttribute(ByteCode code) {
    int attribute_name_index = constantPool.addUtf8_info("Code");

    insert(new Code_attribute(attribute_name_index, code));
  }

  public void addSourceFileAttribute(String sourcefile) {
    int attribute_name_index = constantPool.addUtf8_info("SourceFile");
    int sourcefile_index = constantPool.addUtf8_info(sourcefile);

    insert(new SourceFile_attribute(attribute_name_index, sourcefile_index));
  }
}