package com.adamthorpe.javacompiler.Types.Attributes;

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
