package com.adamthorpe.javacompiler.Types.Tables;

import com.adamthorpe.javacompiler.Types.Interface_info;

public class InterfaceTable extends DataTable<Interface_info>{

  private static final long serialVersionUID = 1L;
  private ConstantPool constantPool;

  public InterfaceTable(ConstantPool constantPool) {
    super();
    this.constantPool = constantPool;
  }

  //WIP
  
}
