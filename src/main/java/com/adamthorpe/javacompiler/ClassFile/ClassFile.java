package com.adamthorpe.javacompiler.ClassFile;

import com.adamthorpe.javacompiler.ClassFile.Attributes.AttributesTable;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.ConstantPool;
import com.adamthorpe.javacompiler.ClassFile.FieldsOrMethods.FieldOrMethodTable;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

public class ClassFile {
  private byte[] magic; // u4
  private byte[] minor_version; // u2
  private byte[] major_version; // u2

  private byte[] constant_pool_count; // u2
  private byte[] constant_pool; // cp_info

  private byte[] access_flags; // u2
  private byte[] this_class; // u2
  private byte[] super_class; // u2

  private byte[] interfaces_count; // u2
  private byte[] interfaces; // u2

  private byte[] fields_count; // u2
  private byte[] fields; // fields_info

  private byte[] methods_count; // u2
  private byte[] methods; // methods_info

  private byte[] attributes_count; // u2
  private byte[] attributes; // attributes_info

  public ClassFile(ConstantPool constant_pool, int this_class, int super_class, byte[] interface_table,
      FieldOrMethodTable field_table, FieldOrMethodTable method_table, AttributesTable attribute_table)
      throws Exception {

    // Version Info
    this.magic = ByteConvert.hexToByteArray("CAFEBABE"); // Magic Number used for every java class file

    this.minor_version = ByteConvert.intToBytes(2, 0); // Minor version is always 0
    this.major_version = ByteConvert.intToBytes(2, 55); // JavaSE version 11 = 55

    // Constant Pool
    this.constant_pool_count = ByteConvert.intToBytes(2, constant_pool.size());
    this.constant_pool = constant_pool.getData();

    // General Info
    this.access_flags = ByteConvert.hexToByteArray("0021"); // public, super
    this.this_class = ByteConvert.intToBytes(2, this_class);
    this.super_class = ByteConvert.intToBytes(2, super_class);

    // Interface Table
    this.interfaces_count = ByteConvert.intToBytes(2, interface_table.length);//TODO
    this.interfaces = interface_table;

    // Field Table
    this.fields_count = ByteConvert.intToBytes(2, field_table.size());
    this.fields = field_table.getData();

    // Method Table
    this.methods_count = ByteConvert.intToBytes(2, method_table.size());
    this.methods = method_table.getData();

    // Attribute Table
    this.attributes_count = ByteConvert.intToBytes(2, attribute_table.size());//TODO
    this.attributes = attribute_table.getData();
  }


  public byte[] getData() {
    return ByteConvert.toByteArr(magic,
      minor_version,
      major_version,
      constant_pool_count,
      constant_pool,
      access_flags,
      this_class,
      super_class,
      interfaces_count,
      interfaces,
      fields_count,
      fields,
      methods_count,
      methods,
      attributes_count,
      attributes);
  }
}