package com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable;

import java.util.List;

import com.adamthorpe.javacompiler.ClassFile.DataTable;
import com.adamthorpe.javacompiler.ClassFile.Code.ByteCode;
import com.adamthorpe.javacompiler.ClassFile.Code.Instruction;
import com.adamthorpe.javacompiler.ClassFile.Code.JumpInstruction;
import com.adamthorpe.javacompiler.ClassFile.LocalVariables.LocalVariable;
import com.adamthorpe.javacompiler.ClassFile.LocalVariables.LocalVariableTable;
import com.adamthorpe.javacompiler.ClassFile.OperandStack.OperandStack;
import com.adamthorpe.javacompiler.ClassFile.OperandStack.OperandStackItem;

public class StackMapEntries extends DataTable<StackMapFrame> {

  protected static final long serialVersionUID = 1L;
  protected ByteCode code;
  protected LocalVariableTable localVariableTable;
  protected OperandStack operandStack;

  public StackMapEntries(ByteCode code, LocalVariableTable localVariableTable, OperandStack operandStack) {
    this.code=code;
    this.localVariableTable=localVariableTable;
    this.operandStack=operandStack;
  }

  public void generateEntries() {
    for (Instruction instruction : code) {

      if (instruction.isJumpInstruction()) {
        JumpInstruction jumpInstruction=instruction.toJumpInstruction();
        int index = jumpInstruction.getIndex();

        //Calculate current stack items and offset
        List<LocalVariable> currentLocals = localVariableTable.getCurrentLocals(index);
        List<OperandStackItem> currentOperands = operandStack.getCurrentLocals(index);
        int offset = index + jumpInstruction.calculateJumpOffset();
        
        addFrame(offset, currentLocals, currentOperands);
      }
    }
  }

  private void addFrame(int offset, List<LocalVariable> currentLocals, List<OperandStackItem> currentOperands) {

    if (isEmpty()) {
      add(new StackMapFrame(offset, currentLocals, currentOperands));
      
    } else {
      StackMapFrame previousFrame = get(size()-1);
      int previousOffset = previousFrame.getOffset();

      if (offset<previousOffset) {
        remove(previousFrame);
        addFrame(offset, currentLocals, currentOperands);

      } else if(offset!=previousOffset) {
        add(new StackMapFrame(offset, currentLocals, currentOperands, previousFrame));
      } 
    }
  }
}