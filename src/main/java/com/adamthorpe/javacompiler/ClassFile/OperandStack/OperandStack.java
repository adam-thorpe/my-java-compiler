package com.adamthorpe.javacompiler.ClassFile.OperandStack;

import java.util.ArrayList;
import java.util.List;

public class OperandStack extends ArrayList<OperandStackItem> {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public OperandStack() {
    super();
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