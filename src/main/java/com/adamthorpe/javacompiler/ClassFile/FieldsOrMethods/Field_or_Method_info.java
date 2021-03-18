package com.adamthorpe.javacompiler.ClassFile.FieldsOrMethods;

import com.adamthorpe.javacompiler.ClassFile.ByteData;
import com.adamthorpe.javacompiler.ClassFile.Attributes.AttributesTable;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>This class describes a field or method in the field/method table.</p>
 * 
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.5
 * https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.6</p>
 */
public class Field_or_Method_info implements ByteData {

  protected byte[] access_flags;     //u2
  protected byte[] name_index;       //u2
  protected byte[] descriptor_index; //u2
  protected byte[] attributes_count; //u2
  protected byte[] attributes;       //attributes_count

  public Field_or_Method_info(int name_index, int accessFlags, int descriptor_index, AttributesTable attributes) {
    this.access_flags = ByteConvert.intToBytes(2, accessFlags);
    this.name_index = ByteConvert.intToBytes(2, name_index);
    this.descriptor_index = ByteConvert.intToBytes(2, descriptor_index);

    this.attributes_count = ByteConvert.intToBytes(2, attributes.size());
    this.attributes = attributes.getData();
  }

  @Override
  public int getLength() {
    return access_flags.length +
      name_index.length +
      descriptor_index.length +
      attributes_count.length +
      attributes.length;
  }
  
  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(access_flags, 
      name_index, 
      descriptor_index, 
      attributes_count, 
      attributes);
  }
}
