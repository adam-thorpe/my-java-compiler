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
   * <p>Add an instruction to the list of bytecode instructions.</p>
   * 
   * @param instruction A new instruction
   */
  public void addInstruction(Instruction instruction) {
    instruction.setIndex(currentIndex);
    this.add(instruction);
    currentIndex+=instruction.getOpCode().getLen();
  }

  /**
   * <p>Add an instruction to the list of bytecode instructions. This method will create the
   * instruction from a given opcode</p>
   * 
   * @param op  OpCode of the instruction
   */
  public void addInstruction(OpCode op) {
    this.add(new Instruction(op, currentIndex));
    currentIndex+=op.getLen();
  }

  /**
   * <p>Add an instruction to the list of bytecode instructions. This method will create the
   * instruction from a given opcode and it's arguments</p>
   * 
   * @param op    OpCode of the instruction
   * @param bytes Number of bytes needed for <code>args</code>
   * @param args  Aditional arguments
   */
  public void addInstruction(OpCode op, int bytes, int args) {
    this.add(new Instruction(op, currentIndex, bytes, args));
    currentIndex+=op.getLen();
  }

  /**
   * <p>Add a jump instruction to the list of bytecode instructions. This instruction is special, 
   * beacause it will contain a link to another instruction of which it points to.</p>
   * 
   * @param op      OpCode of the instruction
   * @param jumpTo  Instruction to jump to
   */
  public void addJumpInstruction(OpCode op, Instruction jumpTo) {
    this.add(new JumpInstruction(op, currentIndex, jumpTo));
    currentIndex+=op.getLen();
  }

  /**
   * <p>Add a jump instruction to the list of bytecode instructions. This instruction is special, 
   * beacause it will contain a link to another instruction of which it points to.</p>
   * 
   * @param op                OpCode of the instruction
   * @param jumpTo            Instruction to jump to
   * @param jumpToIndexOffset Additional offset
   */
  public void addJumpInstruction(OpCode op, Instruction jumpTo, int jumpToIndexOffset) {
    this.add(new JumpInstruction(op, currentIndex, jumpTo, jumpToIndexOffset));
    currentIndex+=op.getLen();
  }

  /**
   * <p>Increase the max stack size.</p>
   * 
   * @param increment Increment to be added to <code>max_stack</code>
   */
  public void addMaxStack(int increment) {
    max_stack+=increment;
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
  public void addMaxLocals(int increment) {
    max_locals+=increment;
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