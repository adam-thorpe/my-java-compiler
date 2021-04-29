package com.adamthorpe.javacompiler.ClassFile.Attributes.SourceFile;

import com.adamthorpe.javacompiler.ClassFile.Attributes.Attributes_info;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>Contains data for the SourceFile attribute.</p>
 * 
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.7.10</p>
 */
public class SourceFile_attribute extends Attributes_info {

  protected byte[] sourcefile_index; //u2

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
    return ByteConvert.copyBytes(attribute_name_index,
      attribute_length,
      sourcefile_index);
  }
}
