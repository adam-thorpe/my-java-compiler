package com.adamthorpe.javacompiler.ClassFile;

import com.adamthorpe.javacompiler.ClassFile.Attributes.AttributesTable;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.ConstantPool;
import com.adamthorpe.javacompiler.ClassFile.FieldsOrMethods.FieldOrMethodTable;
import com.adamthorpe.javacompiler.ClassFile.Interfaces.InterfaceTable;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>This is the final resulting class file. Each field describes a section of data that must be
 * collected to satisfy the class file format.</p>
 * 
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html</p>
 */
public class ClassFile implements ByteData {
  private byte[] magic;               //u4
  private byte[] minor_version;       //u2
  private byte[] major_version;       //u2

  private byte[] constant_pool_count; //u2
  private byte[] constant_pool;       //cp_info

  private byte[] access_flags;        //u2
  private byte[] this_class;          //u2
  private byte[] super_class;         //u2

  private byte[] interfaces_count;    //u2
  private byte[] interfaces;          //u2

  private byte[] fields_count;        //u2
  private byte[] fields;              //fields_info

  private byte[] methods_count;       //u2
  private byte[] methods;             //methods_info

  private byte[] attributes_count;    //u2
  private byte[] attributes;          //attributes_info

  public ClassFile(ConstantPool constant_pool, int this_class, int super_class, InterfaceTable interface_table,
      FieldOrMethodTable field_table, FieldOrMethodTable method_table, AttributesTable attribute_table)
      throws Exception {

    // Version Info
    this.magic = ByteConvert.hexToBytes("CAFEBABE"); // Magic Number used for every java class file
    this.minor_version = ByteConvert.intToBytes(2, 0); // Minor version is always 0
    this.major_version = ByteConvert.intToBytes(2, 55); // JavaSE version 11 = 55

    // Constant Pool
    this.constant_pool_count = ByteConvert.intToBytes(2, constant_pool.size());
    this.constant_pool = constant_pool.getData();

    // General Info
    this.access_flags = ByteConvert.hexToBytes("0021"); // public, super
    this.this_class = ByteConvert.intToBytes(2, this_class);
    this.super_class = ByteConvert.intToBytes(2, super_class);

    // Interface Table
    this.interfaces_count = ByteConvert.intToBytes(2, interface_table.size());
    this.interfaces = interface_table.getData();

    // Field Table
    this.fields_count = ByteConvert.intToBytes(2, field_table.size());
    this.fields = field_table.getData();

    // Method Table
    this.methods_count = ByteConvert.intToBytes(2, method_table.size());
    this.methods = method_table.getData();

    // Attribute Table
    this.attributes_count = ByteConvert.intToBytes(2, attribute_table.size());
    this.attributes = attribute_table.getData();
  }

  @Override
  public byte[] getData() {
    return ByteConvert.copyBytes(magic,
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

  @Override
  public int getLength() {
    return magic.length +
      minor_version.length +
      major_version.length +
      constant_pool_count.length +
      constant_pool.length +
      access_flags.length +
      this_class.length +
      super_class.length +
      interfaces_count.length +
      interfaces.length +
      fields_count.length +
      fields.length +
      methods_count.length +
      methods.length +
      attributes_count.length +
      attributes.length;
  }
}
