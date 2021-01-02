package com.adamthorpe.javacompiler;

import java.util.List;

import com.adamthorpe.javacompiler.Types.Attributes.Attributes_info;
import com.adamthorpe.javacompiler.Types.Tables.AttributesTable;
import com.adamthorpe.javacompiler.Types.Tables.ConstantPool;
import com.adamthorpe.javacompiler.Types.Tables.FieldOrMethodTable;

public class ClassFile {
  private byte[] magic; //u4
  private byte[] minor_version; //u2
  private byte[] major_version; // u2

  private byte[] constant_pool_count; //u2
  private byte[] constant_pool; //cp_info

  private byte[] access_flags; //u2
  private byte[] this_class; //u2
  private byte[] super_class; //u2

  private byte[] interfaces_count; //u2
  private byte[] interfaces; //u2

  private byte[] fields_count; //u2
  private byte[] fields; //fields_info

  private byte[] methods_count; //u2
  private byte[] methods; //methods_info

  private byte[] attributes_count; //u2
  private byte[] attributes; //attributes_info

  public ClassFile(ConstantPool constant_pool, 
    int this_class, 
    int super_class,
    byte[] interface_table, 
    FieldOrMethodTable field_table, 
    FieldOrMethodTable method_table, 
    AttributesTable attribute_table
  ) {

    // Version Info
    this.magic = hexToByteArray("CAFEBABE"); //Magic Number used for every java class file
    this.minor_version = hexToByteArray("0000"); //Minor version is 0
    this.major_version = hexToByteArray("0037"); //JavaSE version 11 = Hex 37

    // Constant Pool
    this.constant_pool_count = ByteConvert.intToBytes(2, constant_pool.size() + 1);
    this.constant_pool = constant_pool.getData();

    // General Info
    this.access_flags = hexToByteArray("0021");
    this.this_class = ByteConvert.intToBytes(2, this_class);
    this.super_class = ByteConvert.intToBytes(2, super_class);

    // Interface Table
    this.interfaces_count = ByteConvert.intToBytes(2, interface_table.length);//todo
    this.interfaces = interface_table;

    // Field Table
    this.fields_count = ByteConvert.intToBytes(2, field_table.size());
    this.fields = field_table.getData();

    // Method Table
    this.methods_count = ByteConvert.intToBytes(2, method_table.size());
    this.methods = method_table.getData();

    // Attribute Table
    this.attributes_count = ByteConvert.intToBytes(2, attribute_table.size());//todo
    this.attributes = attribute_table.getData();
  }

  protected static byte[] hexToByteArray(String hex) {
    int len = hex.length();

    byte[] out = new byte[ len/2 ];

    for (int i=0; i<(len/2); i++) {
      int d1 = getDigit(hex.charAt(i*2));
      int d2 = getDigit(hex.charAt(i*2 + 1));

      out[i] = (byte) ((d1 << 4) + d2);
    }

    return out;
  }

  protected static int getDigit(char letter) {
    return Character.digit(letter, 16);
  }


  public byte[] toByteArr() {
    return toByteArr(getFileSize(),
      magic,
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

  protected byte[] toByteArr(int length, byte[] ...data) {
    byte[] result = new byte[length];
    int counter = 0;

    for(byte[] d : data) {
      System.arraycopy(d, 0, result, counter, d.length);
      counter += d.length;
    }

    return result;
  }

  private int getFileSize() {
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
