package com.adamthorpe.javacompiler.Types;

import com.adamthorpe.javacompiler.ByteConvert;
import com.adamthorpe.javacompiler.Types.Attributes.Attributes_info;
import com.adamthorpe.javacompiler.Types.Tables.AttributesTable;

public class Field_or_Method_info implements ByteData {
  protected byte[] access_flags; //u2
  protected byte[] name_index; //u2
  protected byte[] descriptor_index; //u2
  protected byte[] attributes_count; //u2
  protected int attributes_length;
  protected byte[] attributes; //attr_count

  public Field_or_Method_info(int name_index, int descriptor_index, AttributesTable attributes) {
    this.access_flags = ByteConvert.intToBytes(2, 1); //Always public
    this.name_index = ByteConvert.intToBytes(2, name_index);
    this.descriptor_index = ByteConvert.intToBytes(2, descriptor_index);

    this.attributes_length = attributes.size();
    this.attributes_count = ByteConvert.intToBytes(2, attributes_length);
    this.attributes = attributes.getData();
    
  }
  
  @Override
  public byte[] getData() {
    byte[] data = new byte[8+attributes_length];
    
    System.arraycopy(access_flags, 0, data, 0, 2);
    System.arraycopy(name_index, 0, data, 2, 2);
    System.arraycopy(descriptor_index, 0, data, 4, 2);
    System.arraycopy(attributes_count, 0, data, 6, 2);
    System.arraycopy(attributes, 0, data, 8, attributes_length);

    return data;
  }

  @Override
  public int getLength() {
    return access_flags.length +
      name_index.length +
      descriptor_index.length +
      attributes_count.length +
      attributes.length;
  }
}
