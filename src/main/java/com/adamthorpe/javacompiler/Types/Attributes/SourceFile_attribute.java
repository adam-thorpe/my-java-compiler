package com.adamthorpe.javacompiler.Types.Attributes;

import com.adamthorpe.javacompiler.ByteConvert;

public class SourceFile_attribute extends Attributes_info {

  private byte[] sourcefile_index; //u2

  public SourceFile_attribute(int attribute_name_index, int sourcefile_index) {
    super(attribute_name_index);
    this.sourcefile_index = ByteConvert.intToBytes(2, sourcefile_index);
    setAttributeLength(2);
  }

  @Override
  public int getLength() {
    return super.getLength() + 
      sourcefile_index.length;
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(attribute_name_index,
      attribute_length,
      sourcefile_index);
  }
}
