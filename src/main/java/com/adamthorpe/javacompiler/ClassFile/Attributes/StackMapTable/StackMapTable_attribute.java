package com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable;

import com.adamthorpe.javacompiler.ClassFile.Attributes.Attributes_info;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

public class StackMapTable_attribute extends Attributes_info {

  byte[] number_of_entries; //u2
  byte[] entries;

  public StackMapTable_attribute(int attribute_name_index, StackMapEntries entries) {
    super(attribute_name_index);
    this.number_of_entries = ByteConvert.intToBytes(2, entries.getSize());
    this.entries = entries.getData();

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
