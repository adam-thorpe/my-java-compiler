package com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable.VerificationType;

import com.adamthorpe.javacompiler.ClassFile.ByteData;
import com.adamthorpe.javacompiler.Compiler.Type;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>Contains data for the VerificationType_info.</p>
 * 
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.7.4</p>
 */
public class VerificationType_info implements ByteData {
  
  protected VerificationType_tag verificationType_tag;
  protected byte[] tag; //u1

  public VerificationType_info(Type type) {
    if(type.isInt() || type.isBool()) {
     this.verificationType_tag=VerificationType_tag.ITEM_Integer;
    }

    this.tag = ByteConvert.intToBytes(1, verificationType_tag.getTag());
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
