package com.adamthorpe.javacompiler.ClassFile.OperandStack;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.ClassFile.Code.ByteCode;
import com.adamthorpe.javacompiler.Compiler.Type;

public class OperandStack extends ArrayList<OperandStackItem> {

  private static final long serialVersionUID = 1L;
  private ByteCode code;

  public OperandStack(ByteCode code) {
    super();
    this.code=code;
  }

  /**
   * 
   * 
   * @param operandType
   * @param index
   */
  public void addStackItem(Type operandType, int index) {
    super.add(new OperandStackItem(operandType, index));
    code.addMaxStack();
  }

  /**
   * 
   * 
   * @param index
   * @return
   */
  public List<OperandStackItem> getCurrentLocals(int index) {
    List<OperandStackItem> currentOperand = new ArrayList<>();
    
    for(OperandStackItem operand : this) {
      if (operand.getIndex()<=index && operand.getIndex()>=0) {
        currentOperand.add(operand);
      }
    }

    return currentOperand;
  }
  
}