package com.adamthorpe.javacompiler.Types.Attributes;

import com.adamthorpe.javacompiler.ByteConvert;

public class Code_attribute extends Attributes_info {

  byte[] max_stack; //u2
  byte[] max_locals; //u2
  byte[] code_length; //u4
  int code_length_int;
  byte[] code; //u1 * code_length
  byte[] exception_table_length; //u2
  int exception_table_length_int;
  Exception_table[] exception_table; //u8 * exception_table_length
  byte[] attributes_count; //u2
  int attributes_count_int;
  byte[] attributes; //attributes_count

  public Code_attribute(int attribute_name_index) {
    super.attribute_name_index = ByteConvert.intToBytes(2, attribute_name_index);
    // super.attribute_length
    
  }

  public byte[] getData() {
    byte[] data = new byte[18+code_length_int+8*exception_table_length_int+attributes_count_int];
    System.arraycopy(super.getData(), 0, data, 0, 6);
    
    System.arraycopy(max_stack, 0, data, 6, 2);
    System.arraycopy(max_locals, 0, data, 8, 2);
    System.arraycopy(code_length, 0, data, 10, 4);
    System.arraycopy(code, 0, data, 14, code_length_int);
    System.arraycopy(exception_table_length, 0, data, 14+code_length_int, 2);
    System.arraycopy(exception_table, 0, data, 16+code_length_int, 8*exception_table_length_int);
    System.arraycopy(attributes_count, 0, data, 16+code_length_int+8*exception_table_length_int, 2);
    System.arraycopy(attributes, 0, data, 18+code_length_int+8*exception_table_length_int, attributes_count_int);

    return data;
  }
}