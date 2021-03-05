package com.adamthorpe.javacompiler.Types.Attributes;

import com.adamthorpe.javacompiler.ByteConvert;

public class StackMapTable_attribute extends Attributes_info {

  byte[] number_of_entries; //u2
  byte[] entries;

  public StackMapTable_attribute(int attribute_name_index) {
    super(attribute_name_index);
    this.number_of_entries = new byte[2];
    this.entries = new byte[0];

    setAttributeLength(getThisLength());
  }

  public int getThisLength() {
    return number_of_entries.length +
      entries.length;
  }

  @Override
  public int getLength() {
    return super.getLength() +
      getThisLength();
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(attribute_name_index,
      attribute_length,
      number_of_entries,
      entries
    );
  }

}
