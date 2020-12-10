package com.adamthorpe.javacompiler;

import java.util.ArrayList;

import com.adamthorpe.javacompiler.ConstantPoolTypes.CONSTANT;

public class ClassFile {
  private byte[] magic_number; //u4
  private byte[] minor_version; //u2
  private byte[] major_version; // u2

  private byte[] constant_pool_size; //u2
  private byte[] constant_pool; //cpsize

  private byte[] access_flags; //u2
  private byte[] this_class; //u2
  private byte[] super_class; //u2

  private byte[] interface_table_size; //u2
  private byte[] interface_table; //isize

  private byte[] field_table_size; //u2
  private byte[] field_table; //fsize

  private byte[] method_table_size; //u2
  private byte[] method_table; //msize

  private byte[] attribute_table_size; //u2
  private byte[] attribute_table; //asize

  public ClassFile(ConstantPool constant_pool, int this_class, int super_class,
    byte[] interface_table, byte[] field_table, byte[] method_table, byte[] attribute_table) {

    // Version Info
    this.magic_number = hexToByteArray("CAFEBABE"); //Magic Number used for every java class file
    this.minor_version = hexToByteArray("0000"); //Minor version is 0
    this.major_version = hexToByteArray("0037"); //JavaSE version 11 = Hex 37

    // Constant Pool
    this.constant_pool_size = ByteConvert.intToBytes(2, constant_pool.size() + 1);
    this.constant_pool = new byte[constant_pool.getLength()];
    int counter = 0;

    for(CONSTANT entry : constant_pool) {
      System.arraycopy(entry.getData(), 0, this.constant_pool, counter, entry.getData().length);
      counter += entry.getLength();
    }

    // General Info
    this.access_flags = hexToByteArray("0021");
    this.this_class = ByteConvert.intToBytes(2, this_class);
    this.super_class = ByteConvert.intToBytes(2, super_class);

    // Interface Table
    this.interface_table_size = ByteConvert.intToBytes(2, interface_table.length);//todo
    this.interface_table = interface_table;

    // Field Table
    this.field_table_size = ByteConvert.intToBytes(2, field_table.length);//todo
    this.field_table = field_table;

    // Method Table
    this.method_table_size = ByteConvert.intToBytes(2, method_table.length);//todo
    this.method_table = method_table;

    // Attribute Table
    this.attribute_table_size = ByteConvert.intToBytes(2, attribute_table.length);//todo
    this.attribute_table = attribute_table;
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


  protected byte[] toByteArr() {
    return toByteArr(getFileSize(),
      magic_number,
      minor_version,
      major_version,
      constant_pool_size,
      constant_pool,
      access_flags,
      this_class,
      super_class,
      interface_table_size,
      interface_table,
      field_table_size,
      field_table,
      method_table_size,
      method_table,
      attribute_table_size,
      attribute_table);
  }

  public byte[] toByteArr(int length, byte[] ...data) {
    byte[] result = new byte[length];
    int counter = 0;

    for(byte[] d : data) {
      System.arraycopy(d, 0, result, counter, d.length);
      counter += d.length;
    }

    return result;
  }

  private int getFileSize() {
    return magic_number.length +
      minor_version.length +
      major_version.length +
      constant_pool_size.length + 
      constant_pool.length + 
      access_flags.length + 
      this_class.length + 
      super_class.length + 
      interface_table_size.length +
      interface_table.length +
      field_table_size.length + 
      field_table.length + 
      method_table_size.length + 
      method_table.length +
      attribute_table_size.length + 
      attribute_table.length;
  }
}
