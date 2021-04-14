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

  public StackMapFrame(int offset, List<LocalVariable> localVariables, List<OperandStackItem> operandStack, StackMapFrame previousFrame) {
    this.offset=offset;
    this.localVariables=localVariables;
    this.operandStack=operandStack;

    int newOffset = offset-previousFrame.getOffset()-1;

    List<LocalVariable> newLocals = new ArrayList<>(localVariables);
    newLocals.removeAll(previousFrame.getLocals());

    List<LocalVariable> chopLocals = new ArrayList<>(previousFrame.getLocals());
    chopLocals.removeAll(localVariables);

    List<OperandStackItem> newOperands = new ArrayList<>(operandStack);
    newOperands.removeAll(previousFrame.getOperands());

    generateData(newOffset, newLocals, chopLocals, newOperands);
  }

  public StackMapFrame(int offset, List<LocalVariable> localVariables, List<OperandStackItem> operandStack) {
    this.offset=offset;
    this.localVariables=localVariables;
    this.operandStack=operandStack;

    generateData(offset, localVariables, new ArrayList<>(), operandStack);
  }

  public void generateData(int offsetDiff, List<LocalVariable> newLocals, List<LocalVariable> chopLocals, List<OperandStackItem> newOperands) {

    if (newLocals.isEmpty() && chopLocals.isEmpty()) {
      if (newOperands.isEmpty()) {
        //SAME
        if (offsetDiff<64) {
          this.frame_type=ByteConvert.intToBytes(1, offsetDiff);
          this.offset_delta=new byte[0];
    
        //SAME_FRAME_EXTENDED
        } else {
          this.frame_type=ByteConvert.intToBytes(1, 251);
          this.offset_delta=ByteConvert.intToBytes(2, offsetDiff);
        }

        this.stack=new byte[0];
        return;

      } else if(newOperands.size()==1) {
        //SAME_LOCALS_1_STACK_ITEM
        if (offsetDiff<64) {
          this.frame_type=ByteConvert.intToBytes(1, offsetDiff+64);
          this.offset_delta=new byte[0];

        //SAME_LOCALS_1_STACK_ITEM_EXTENDED
        } else { 
          this.frame_type=ByteConvert.intToBytes(1, 247);
          this.offset_delta=ByteConvert.intToBytes(2, offsetDiff);
        }

        this.stack = new VerificationType_info(newOperands.get(0).getType()).getData();
        return;
      }

    //APPEND
    } else if (newLocals.size()<=3 && chopLocals.isEmpty()) {
      this.frame_type=ByteConvert.intToBytes(1, 251+newLocals.size());
      this.offset_delta=ByteConvert.intToBytes(2, offsetDiff);
      
      List<VerificationType_info> stack = new ArrayList<>();
      for(LocalVariable local : newLocals) {
        stack.add(new VerificationType_info(local.getType()));
      }

      this.stack=ByteConvert.toByteArr(stack);
      return;

    //CHOP
    } else if (chopLocals.size()<=3 && newLocals.isEmpty()) {
      System.err.println("CHOP FRAME");

      chopLocals.forEach(l -> System.out.println(l.getName()));
    //FULL
    } else {
      System.err.println("FULL FRAME");
    }

    System.err.println("Invalid StackMapFrame Created");
    this.frame_type=new byte[0];
    this.offset_delta=new byte[0];
    this.stack=new byte[0];
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