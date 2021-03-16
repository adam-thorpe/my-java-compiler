package com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable.VerificationType;

import com.adamthorpe.javacompiler.ClassFile.ByteData;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

public class VerificationType_info implements ByteData {
  
  protected byte[] tag; //u1

  public VerificationType_info(VerificationType_tag tag) {
    this.tag = ByteConvert.intToBytes(1, tag.getTag());
  }

  @Override
  public int getLength() {
    return tag.length;
  }

  @Override
  public byte[] getData() {
    return tag;
  }


}
