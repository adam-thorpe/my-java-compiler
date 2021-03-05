package com.adamthorpe.javacompiler.Types.Attributes;

import com.adamthorpe.javacompiler.ByteConvert;
import com.adamthorpe.javacompiler.Types.ByteData;

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
