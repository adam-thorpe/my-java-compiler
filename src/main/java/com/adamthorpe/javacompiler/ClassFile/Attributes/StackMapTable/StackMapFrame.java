package com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.ClassFile.ByteData;
import com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable.VerificationType.VerificationType_info;
import com.adamthorpe.javacompiler.ClassFile.LocalVariables.LocalVariable;
import com.adamthorpe.javacompiler.ClassFile.OperandStack.OperandStackItem;
import com.adamthorpe.javacompiler.Utilities.ByteConvert;

/**
 * <p>Contains data for a single StackMapFrame.</p>
 * 
 * <p>To find out more, please visit: https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.7.4</p>
 */
public class StackMapFrame implements ByteData {

  protected byte[] frame_type;    //u1
  protected byte[] offset_delta;  //u2 (optional)
  protected byte[] stack;         //variable (optional)

  protected int offset;
  protected List<LocalVariable> localVariables;
  protected List<OperandStackItem> operandStack;

  public StackMapFrame(int offset, int varOffset, List<LocalVariable> localVariables, List<OperandStackItem> operandStack) {
    this.offset=offset;
    this.localVariables=localVariables;
    this.operandStack=operandStack;

    if (localVariables.isEmpty()) {
      if (operandStack.isEmpty()) {
        //SAME
        if (varOffset<64) { 
          this.frame_type=ByteConvert.intToBytes(1, varOffset);
          this.offset_delta=new byte[0];
    
        //SAME_FRAME_EXTENDED
        } else {
          this.frame_type=ByteConvert.intToBytes(1, 251);
          this.offset_delta=ByteConvert.intToBytes(2, varOffset);
        }

        this.stack=new byte[0];

      } else if(operandStack.size()==1) {
        //SAME_LOCALS_1_STACK_ITEM
        if (varOffset<64) {
          this.frame_type=ByteConvert.intToBytes(1, varOffset+64);
          this.offset_delta=new byte[0];

        //SAME_LOCALS_1_STACK_ITEM_EXTENDED
        } else { 
          this.frame_type=ByteConvert.intToBytes(1, 247);
          this.offset_delta=ByteConvert.intToBytes(2, varOffset);
        }

        this.stack = new VerificationType_info(operandStack.get(0).getType()).getData();
      }

    //APPEND
    } else if (localVariables.size()<=3) {
      this.frame_type=ByteConvert.intToBytes(1, 251+localVariables.size());
      this.offset_delta=ByteConvert.intToBytes(2, varOffset);
      
      List<VerificationType_info> stack = new ArrayList<>();
      for(LocalVariable localVariable : localVariables) {
        stack.add(new VerificationType_info(localVariable.getType()));
      }

      this.stack=ByteConvert.toByteArr(stack);
    }
  }

  public int getOffset() {
    return offset;
  }

  public List<LocalVariable> getLocals() {
    return localVariables;
  }

  public List<OperandStackItem> getOperands() {
    return operandStack;
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