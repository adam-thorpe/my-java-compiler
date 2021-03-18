package com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.ClassFile.DataTable;
import com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable.VerificationType.VerificationType_info;
import com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable.VerificationType.VerificationType_tag;
import com.adamthorpe.javacompiler.ClassFile.Code.JumpInstruction;
import com.adamthorpe.javacompiler.ClassFile.Code.OpCode;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

public class StackMapEntries extends DataTable<StackMapFrame> {

  protected static final long serialVersionUID = 1L;
  protected List<JumpInstruction> instructions;
  protected int previous;

  public StackMapEntries() {
    instructions = new ArrayList<>();
    previous=0;
  }

  /**
   * <p>Insert a new instruction into the list of instructions.</p>
   * 
   * @param i The instruction
   */
  public void insertInstruction(JumpInstruction i) {
    instructions.add(i);
  }

  /**
   * <p>Generates the StackMapTable entries.</p>
   */
  protected void generateEntries() {
    for(JumpInstruction instruction : instructions) {

      if(instruction.getOpCode()==OpCode.goto_) {
        List<VerificationType_info> stack = new ArrayList<>();
        stack.add(new VerificationType_info(VerificationType_tag.ITEM_Integer));
        add(new StackMapFrame(64, stack));
      } else {
        int jumpIndex = instruction.calculateJump()+instruction.getIndex();

        if (previous==0) {
          add(new StackMapFrame(jumpIndex));

        } else if (previous==jumpIndex) {
          //Do nothing
        } else if (previous<jumpIndex) {
          add(new StackMapFrame(jumpIndex-previous-1));
        }
        previous=jumpIndex;
      }
    }
  }

  /**
   * <p>Checks whether there are any instructions stored in the StackMap.</p>
   * 
   * @return <code>True</code> if there are no entries
   */
  public boolean instructionsIsEmpty() {
    return instructions.isEmpty();
  }

  @Override
  public int size() {
    if (isEmpty()) generateEntries();
    return super.size();
  }

  @Override
  public byte[] getData() {
    if (isEmpty()) generateEntries();
    return ByteConvert.toByteArr(this);
  }

  @Override
  public int getLength() {
    if (isEmpty()) generateEntries();
    return super.getLength();
  }
}
