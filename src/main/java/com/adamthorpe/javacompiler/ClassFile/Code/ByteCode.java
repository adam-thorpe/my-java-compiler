package com.adamthorpe.javacompiler.ClassFile.Code;

import com.adamthorpe.javacompiler.ClassFile.DataTable;

/**
 * <p>Contains a list of instructions in ByteCode form and any other assets.</p>
 */
public class ByteCode extends DataTable<Instruction> {

  protected static final long serialVersionUID = 1L;
  protected int max_stack;
  protected int max_locals;
  protected int currentIndex;

  public ByteCode() {
    max_stack=1;
    max_locals=1;
    currentIndex=0;
  }

  /**
   * <p>Add an instruction to the list of bytecode instructions. This method will create the
   * instruction from a given opcode.</p>
   * 
   * @param op  OpCode of the instruction
   * @return    Index of the instruction
   */
  public int addInstruction(OpCode op) {
    int thisIndex = currentIndex;

    this.add(new Instruction(op, thisIndex));
    currentIndex+=op.getLen();

    return thisIndex;
  }

  /**
   * <p>Add an instruction to the list of bytecode instructions. This method will create the
   * instruction from a given opcode and it's arguments.</p>
   * 
   * @param op    OpCode of the instruction
   * @param bytes Number of bytes needed for <code>args</code>
   * @param args  Aditional arguments
   * @return      Index of the instruction
   */
  public int addInstruction(OpCode op, int bytes, int args) {
    int thisIndex = currentIndex;

    this.add(new Instruction(op, thisIndex, bytes, args));
    currentIndex+=op.getLen();

    return thisIndex;
  }

  /**
   * <p>Add a jump instruction to the list of bytecode instructions. The argument of this 
   * instruction will be filled in later.</p>
   * 
   * @param op  OpCode of the instruction
   * @return    The Jump Instruction
   */
  public JumpInstruction addJumpInstruction(OpCode op) {
    JumpInstruction jumpInstruction = new JumpInstruction(op, currentIndex);

    this.add(jumpInstruction);
    currentIndex+=op.getLen();

    return jumpInstruction;
  }

  /**
   * <p>Increase the max stack size.</p>
   * 
   * @param increment Increment to be added to <code>max_stack</code>
   */
  public void addMaxStack() {
    max_stack+=1;
  }

  /**
   * <p>Get the max stack size.</p>
   * 
   * @return  <code>max_stack</code> of the attribute
   */
  public int getMaxStack() {
    return max_stack;
  }

  /**
   * <p>Increase the max locals.</p>
   * 
   * @param increment Increment to be added to <code>max_locals</code>
   */
  public void addMaxLocals() {
    max_locals+=1;
  }

  /**
   * <p>Get the max locals.</p>
   * 
   * @return  <code>max_locals</code> of the attribute
   */
  public int getMaxLocals() {
    return max_locals;
  }

  /**
   * <p>Get the current instruction index.</p>
   * 
   * @return  Current index
   */
  public int getCurrentIndex() {
    return currentIndex;
  }
}