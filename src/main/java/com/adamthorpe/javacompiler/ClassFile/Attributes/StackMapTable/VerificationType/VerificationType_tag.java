package com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable.VerificationType;

/**
 * <p>Contains the ID for a VerificationTypeInfo Tag .</p>
 * 
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.7.4</p>
 */
public enum VerificationType_tag {
  ITEM_Top(0),
  ITEM_Integer(1);

  protected final int tag;

  private VerificationType_tag(int tag) {
    this.tag=tag;
  }

  public int getTag() {
    return tag;
  }
}