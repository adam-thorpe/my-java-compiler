package com.adamthorpe.javacompiler.Types.Attributes;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.ByteConvert;
import com.adamthorpe.javacompiler.Types.ByteData;
import com.adamthorpe.javacompiler.Types.Code.JumpInstruction;
import com.adamthorpe.javacompiler.Types.Code.OpCode;

public class StackMapEntries implements ByteData {

  private List<JumpInstruction> instructions;
  private int previous;
  private List<StackMapFrame> entries;

  public StackMapEntries() {
    previous=0;
    instructions = new ArrayList<>();
    entries = new ArrayList<>();
  }

  public void insertInstruction(JumpInstruction i) {
    instructions.add(i);
  }

  private List<StackMapFrame> generateEntries() {
    for(JumpInstruction instruction : instructions) {

      if(instruction.getOpCode()==OpCode.goto_) {
        List<VerificationType_info> stack = new ArrayList<>();
        stack.add(new VerificationType_info(VerificationType_tag.ITEM_Integer));
        entries.add(new StackMapFrame(64, stack));
      } else {
        int jumpIndex = instruction.calculateJump()+instruction.getIndex();

        if (previous==0) {
          entries.add(new StackMapFrame(jumpIndex));

        } else if (previous==jumpIndex) {
          //Do nothing
        } else if (previous<jumpIndex) {
          entries.add(new StackMapFrame(jumpIndex-previous-1));
        }
        previous=jumpIndex;
      }
    }

    return entries;
  }

  public boolean isEmpty() {
    return instructions.isEmpty();
  }

  public int getSize() {
    if (entries.isEmpty()) generateEntries();
    return entries.size();
  }

  @Override
  public byte[] getData() {
    if (entries.isEmpty()) generateEntries();
    return ByteConvert.toByteArr(entries);
  }

  @Override
  public int getLength() {
    if (entries.isEmpty()) generateEntries();
    return 0;
  }
}
