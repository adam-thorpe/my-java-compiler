package com.adamthorpe.javacompiler.ClassFile.Attributes.Code;

import com.adamthorpe.javacompiler.ClassFile.Attributes.AttributesTable;
import com.adamthorpe.javacompiler.ClassFile.Attributes.Attributes_info;
import com.adamthorpe.javacompiler.ClassFile.Code.ByteCode;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>Contains data for the Code attribute.</p>
 * 
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.7.3</p>
 */
public class Code_attribute extends Attributes_info {

  protected byte[] max_stack;               //u2
  protected byte[] max_locals;              //u2

  protected byte[] code_length;             //u4
  protected byte[] code;                    //u1 * code_length

  protected byte[] exception_table_length;  //u2
  protected byte[] exception_table;         //u8 * exception_table_length

  protected byte[] attributes_count;        //u2
  protected byte[] attributes;              //attributes[attributes_count]

  public Code_attribute(int attribute_name_index, ByteCode code, AttributesTable attributes) {
    super(attribute_name_index);

    this.max_stack = ByteConvert.intToBytes(2, code.getMaxStack());
    this.max_locals = ByteConvert.intToBytes(2, code.getMaxLocals());

    this.code = code.getData();
    this.code_length = ByteConvert.intToBytes(4, code.getLength());

    this.exception_table = new byte[0];
    this.exception_table_length = ByteConvert.intToBytes(2, exception_table.length);

    this.attributes = attributes.getData();
    this.attributes_count = ByteConvert.intToBytes(2, attributes.size());

    setAttributeLength(getThisLength());
  }

  /**
   * <p>Gets the length of the data in this class, used for the <code>super.attribute_length</code>.</p>
   * 
   * @return  Length of the data in this class
   */
  private int getThisLength() {
    return max_stack.length +
      max_locals.length +
      code_length.length +
      code.length +
      exception_table_length.length +
      exception_table.length +
      attributes_count.length +
      attributes.length;
  }

  @Override
  public int getLength() {
    return super.getLength() +
      getThisLength();
  }

  @Override
  public byte[] getData() {
    return ByteConvert.copyBytes(attribute_name_index,
      attribute_length,
      max_stack,
      max_locals,
      code_length,
      code,
      exception_table_length,
      exception_table,
      attributes_count,
      attributes
    );
  }
}