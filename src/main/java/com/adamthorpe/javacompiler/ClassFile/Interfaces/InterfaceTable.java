package com.adamthorpe.javacompiler.ClassFile.Interfaces;

import com.adamthorpe.javacompiler.ClassFile.DataTable;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.ConstantPool;

public class InterfaceTable extends DataTable<Interface_info>{

  private static final long serialVersionUID = 1L;
  private ConstantPool constantPool;

  public InterfaceTable(ConstantPool constantPool) {
    super();
    this.constantPool = constantPool;
  }
  
}