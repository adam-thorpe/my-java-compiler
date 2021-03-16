package com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable;

import java.util.List;

import com.adamthorpe.javacompiler.ClassFile.ByteData;
import com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable.VerificationType.VerificationType_info;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

public class StackMapFrame implements ByteData {

  protected byte[] frame_type; //u1
  protected byte[] offset_delta; //optional u2
  protected byte[] stack; //

  public StackMapFrame(int frame_type) {
    this.frame_type = ByteConvert.intToBytes(1, frame_type);
    this.offset_delta = new byte[0];
    this.stack = new byte[0];
  }

  public StackMapFrame(int frame_type, List<VerificationType_info> stack) {
    this.frame_type = ByteConvert.intToBytes(1, frame_type);
    this.offset_delta = new byte[0];
    this.stack = ByteConvert.toByteArr(stack);
  }

  @Override
  public int getLength() {
    return frame_type.length +
      offset_delta.length +
      stack.length;
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(frame_type, offset_delta, stack);
  }
}
