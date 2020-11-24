package com.adamthorpe.javacompiler;

import javassist.bytecode.MethodInfo;

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

  public ClassFile(byte[] constant_pool, byte[] access_flags, byte[] this_class, byte[] super_class,
    byte[] interface_table, byte[] field_table, byte[] method_table, byte[] attribute_table) {

    this.magic_number = hexToByteArray("CAFEBABE"); //Magic Number used for every java class file
    this.minor_version = hexToByteArray("0000"); //Minor version is 0
    this.major_version = hexToByteArray("0037"); //JavaSE version 11 = Hex 37

    this.constant_pool_size = hexToByteArray("0000");//todo
    this.constant_pool = constant_pool;

    this.access_flags = access_flags;
    this.this_class = this_class;
    this.super_class = super_class;

    this.interface_table_size = hexToByteArray("0000");//todo
    this.interface_table = interface_table;

    this.field_table_size = hexToByteArray("0000");//todo
    this.field_table = field_table;

    this.method_table_size = hexToByteArray("0000");//todo
    this.method_table = method_table;

    this.attribute_table_size = hexToByteArray("0000");//todo
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


  protected byte[] toByte() {
    byte[] result = new byte[getFileSize()];
    int counter = 0;

    counter += magic_number.length;
    System.arraycopy(magic_number, 0, result, 0, counter);
    counter += minor_version.length;
    System.arraycopy(minor_version, 0, result, 0, counter);
    counter += major_version.length;
    System.arraycopy(major_version, 0, result, 0, counter);

    counter += constant_pool_size.length;
    System.arraycopy(constant_pool_size, 0, result, 0, counter);
    counter += constant_pool.length;
    System.arraycopy(constant_pool, 0, result, 0, counter);

    counter += access_flags.length;
    System.arraycopy(access_flags, 0, result, 0, counter);
    counter += this_class.length;
    System.arraycopy(this_class, 0, result, 0, counter);
    counter += super_class.length;
    System.arraycopy(super_class, 0, result, 0, counter);

    counter += interface_table_size.length;
    System.arraycopy(interface_table_size, 0, result, 0, counter);
    counter += interface_table.length;
    System.arraycopy(interface_table, 0, result, 0, counter);

    counter += field_table_size.length;
    System.arraycopy(field_table_size, 0, result, 0, counter);
    counter += field_table.length;
    System.arraycopy(field_table, 0, result, 0, counter);

    counter += method_table_size.length;
    System.arraycopy(method_table_size, 0, result, 0, counter);
    counter += method_table.length;
    System.arraycopy(method_table, 0, result, 0, counter);

    counter += attribute_table_size.length;
    System.arraycopy(attribute_table_size, 0, result, 0, counter);
    counter += attribute_table.length;
    System.arraycopy(attribute_table, 0, result, 0, counter);

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
