package com.adamthorpe.javacompiler.Types.Attributes;

import com.adamthorpe.javacompiler.ByteConvert;
import com.adamthorpe.javacompiler.Types.Code.ByteCode;

public class Code_attribute extends Attributes_info {

  protected byte[] max_stack; //u2
  protected byte[] max_locals; //u2

  protected byte[] code_length; //u4
  protected byte[] code; //u1 * code_length

  protected byte[] exception_table_length; //u2
  protected Exception_table[] exception_table; //u8 * exception_table_length

  protected byte[] attributes_count; //u2
  protected byte[] attributes; //attributes_count

  public Code_attribute(int attribute_name_index, ByteCode code) {
    super(attribute_name_index);

    this.code = code.getData();
    this.code_length = ByteConvert.intToBytes(4, code.getLength());

    this.exception_table = new Exception_table[0]; //Empty
    this.exception_table_length = ByteConvert.intToBytes(2, exception_table.length);

    this.attributes = new byte[0]; //Empty
    this.attributes_count = ByteConvert.intToBytes(2, attributes.length);

    this.max_stack = ByteConvert.intToBytes(2, code.getMaxStack());
    this.max_locals = ByteConvert.intToBytes(2, code.getMaxLocals());

    setAttributeLength(getThisLength());
  }

  @Override
  public int getLength() {
    return super.getLength() +
      getThisLength();
  }

  private int getThisLength() {
    return max_stack.length +
      max_locals.length +
      code_length.length +
      code.length +
      exception_table_length.length +
      exception_table.length*8 +
      attributes_count.length +
      attributes.length;
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(attribute_name_index,
      attribute_length,
      max_stack,
      max_locals,
      code_length,
      code,
      exception_table_length,
      getExceptionTableData(),
      attributes_count,
      attributes
    );
  }

  protected byte[] getExceptionTableData() {
    byte[] data = new byte[exception_table.length*8];
    int counter=0;

    for (Exception_table e : exception_table) {
      System.arraycopy(e.getData(), 0, data, counter, 8);
      counter+=8;
    }

    return data;
  }
}