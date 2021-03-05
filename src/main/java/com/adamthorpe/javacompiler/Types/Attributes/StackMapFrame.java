package com.adamthorpe.javacompiler.Types.Attributes;

import java.util.List;

import com.adamthorpe.javacompiler.ByteConvert;
import com.adamthorpe.javacompiler.Types.ByteData;

public class StackMapFrame implements ByteData {

  protected byte[] frame_type; //u1
  protected byte[] offset_data; //optional u2
  protected byte[] stack; //

  public StackMapFrame(int frame_type) {
    this.frame_type = ByteConvert.intToBytes(1, frame_type);
    this.offset_data = new byte[0];
    this.stack = new byte[0];
  }

  public StackMapFrame(int frame_type, List<VerificationType_info> stack) {
    this.frame_type = ByteConvert.intToBytes(1, frame_type);
    this.offset_data = new byte[0];
    this.stack = ByteConvert.toByteArr(stack);
  }

  @Override
  public int getLength() {
    return frame_type.length +
      offset_data.length +
      stack.length;
  }

  @Override
  public byte[] getData() {
    return ByteConvert.toByteArr(frame_type, offset_data, stack);
  }
}
