package com.adamthorpe.javacompiler.Compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.adamthorpe.javacompiler.ClassFile.LocalVariables.LocalVariableTable;
import com.adamthorpe.javacompiler.ClassFile.OperandStack.OperandStack;
import com.adamthorpe.javacompiler.ClassFile.Attributes.AttributesTable;
import com.adamthorpe.javacompiler.ClassFile.Attributes.StackMapTable.StackMapEntries;
import com.adamthorpe.javacompiler.ClassFile.Code.ByteCode;
import com.adamthorpe.javacompiler.ClassFile.Code.JumpInstruction;
import com.adamthorpe.javacompiler.ClassFile.Code.OpCode;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.ConstantPool;
import com.adamthorpe.javacompiler.Utilities.Util;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.WhileStmt;

public class CodeGenerator {

  protected ConstantPool constantPool;
  protected ByteCode code;

  protected AttributesTable codeAttributes;

  protected LocalVariableTable localVariables;
  protected OperandStack operandStack;

  protected String className;

  public CodeGenerator(ConstantPool constantPool, String className, boolean isStaticMethod) {
    this.constantPool = constantPool;
    this.codeAttributes = new AttributesTable(constantPool);

    this.code = new ByteCode();

    this.localVariables = new LocalVariableTable(code, isStaticMethod);
    this.operandStack = new OperandStack(code);

    this.className = className;
  }

  /**
   * <p>Generates bytecode from a method block.</p>
   * 
   * @param block       The block of code to work on
   * @param parameters  Method parameters
   * @return            Resulting method attributes containing bytecode
   */
  public AttributesTable run(BlockStmt block, List<Parameter> parameters) {
    AttributesTable attributes = new AttributesTable(constantPool);

    //Populate LocalVariableTable
    for (Parameter param : parameters) {
      localVariables.add(param.getNameAsString(), new Type(param.getType()), -1);
    }

    boolean hasReturn = evaluateStatement(block);

    if (!hasReturn) code.addInstruction(OpCode.return_);

    StackMapEntries stackMapEntries = new StackMapEntries(code, localVariables, operandStack);
    stackMapEntries.generateEntries();
    if(!stackMapEntries.isEmpty()) codeAttributes.addStackMapTableAttribute(stackMapEntries);

    attributes.addCodeAttribute(code, codeAttributes);
    return attributes;
  }

  /**
   * 
   * 
   * @param statement
   */
  protected boolean evaluateStatement(Statement statement) {
    if (statement.isBlockStmt()) {
      return evaluateStatement(statement.asBlockStmt());

    } else if (statement.isExpressionStmt()) {
      evaluateExpression(statement.asExpressionStmt().getExpression());
      return false;

    } else if (statement.isIfStmt()) {
      return evaluateStatement(statement.asIfStmt());

    } else if (statement.isReturnStmt()) {
      return evaluateStatement(statement.asReturnStmt());
    
    } else if (statement.isWhileStmt()) {
      return evaluateStatement(statement.asWhileStmt());

    } else {
      System.err.println("Unsupported statement: " + statement.toString());
      return false;
    }
  }

  /**
   * 
   * 
   * @param statement
   */
  protected boolean evaluateStatement(BlockStmt statement) {
    boolean hasReturn=false;

    for (Statement s : statement.getStatements()) {
      hasReturn = evaluateStatement(s) || hasReturn;
    }

    return hasReturn;
  }

  /**
   * 
   * 
   * @param statement
   */
  protected boolean evaluateStatement(IfStmt statement) {
    // Condition
    EvaluatedData data = evaluateExpression(statement.getCondition());

    // Get JumpInstruction
    JumpInstruction jump1;
    if (data.hasInstruction()) {
      jump1=data.getInstruction();
    } else {
      jump1=code.addJumpInstruction(OpCode.ifeq);
    }

    // Body
    boolean hasReturnInBody = evaluateStatement(statement.getThenStmt());
    JumpInstruction jump2 = new JumpInstruction(OpCode.nop, -1); // Dummy value
    if (!hasReturnInBody) {
      jump2 = code.addJumpInstruction(OpCode.goto_);
    }

    jump1.setOffset(code.getCurrentIndex()); // Jump to next statement

    // Else/Else if block
    boolean hasReturnInElse = true;
    if(statement.hasElseBlock() || statement.hasElseBranch()) {
      hasReturnInElse = evaluateStatement(statement.getElseStmt().get());
    }

    jump2.setOffset(code.getCurrentIndex()); // Jump to end of block
    return hasReturnInBody && hasReturnInElse;
  }

  /**
   * <p>Evaluates a return statement
   * Eg. <code>return var1;</code> or <code>return 2+3;</code>.</p>
   * 
   * @param expression  Input return expression
   */
  protected boolean evaluateStatement(ReturnStmt statement) {
    Optional<Expression> expression = statement.getExpression();

    if (expression.isEmpty()) {
      code.addInstruction(OpCode.return_);
    } else {
      Type returnType = evaluateExpression(expression.get()).getType();

      if (returnType.isInt() || returnType.isBool()) {
        code.addInstruction(OpCode.ireturn);
      } else if (returnType.getName().equals("java/lang/String")) {
        code.addInstruction(OpCode.areturn);
      }
    }

    return true;
  }

  /**
   * 
   * 
   * @param statement
   * @return
   */
  protected boolean evaluateStatement(WhileStmt statement) {
    
    int startIndex = code.getCurrentIndex();

    // Jump Condition
    JumpInstruction jumpCondition = evaluateExpression(statement.getCondition()).getInstruction();
    boolean hasReturn = evaluateStatement(statement.getBody());

    //Loop instruction
    code.addJumpInstruction(OpCode.goto_, startIndex);

    jumpCondition.setOffset(code.getCurrentIndex()); //Jump to end of block
    return hasReturn;
  }

  /**
   * <p>This method will determine the form of <code>expression</code> and then evaluate it further. This 
   * is a recursive function, as each expression often comprises of sub-expressions.</p>
   * 
   * @param expression  The input expression
   * @return            Expression Type
   */
  protected EvaluatedData evaluateExpression(Expression expression) {
    if (expression.isAssignExpr()) {
      return evaluateExpression(expression.asAssignExpr());

    } else if (expression.isBinaryExpr()) {
      return evaluateExpression(expression.asBinaryExpr());

    } else if (expression.isBooleanLiteralExpr()) {
      return evaluateExpression(expression.asBooleanLiteralExpr());
    
    } else if (expression.isFieldAccessExpr()) {
      return evaluateExpression(expression.asFieldAccessExpr());
    
    } else if (expression.isIntegerLiteralExpr()) {
      return evaluateExpression(expression.asIntegerLiteralExpr());

    } else if (expression.isMethodCallExpr()) {
      return evaluateExpression(expression.asMethodCallExpr());
    
    } else if (expression.isNameExpr()) { 
      return evaluateExpression(expression.asNameExpr());

    } else if (expression.isStringLiteralExpr()) {
      return evaluateExpression(expression.asStringLiteralExpr());

    } else if (expression.isUnaryExpr()) { 
      return evaluateExpression(expression.asUnaryExpr());

    } else if (expression.isVariableDeclarationExpr()) {
      return evaluateExpression(expression.asVariableDeclarationExpr());

    } else {
      System.err.println("Unsupported expression: " + expression.toString());
      return new EvaluatedData(new EmptyType());
    }
  }

  /**
   * 
   * 
   * @param expression
   * @return
   */
  protected EvaluatedData evaluateExpression(AssignExpr expression) {
    
    evaluateExpression(expression.getValue());

    int index = localVariables.find(expression.getTarget().toString());
    if (index==0) {
      code.addInstruction(OpCode.istore_0);
    } else if (index==1) {
      code.addInstruction(OpCode.istore_1);
    } else if (index==2) {
      code.addInstruction(OpCode.istore_2);
    } else if (index==3) {
      code.addInstruction(OpCode.istore_3);
    }

    return new EvaluatedData(new EmptyType());
  }

  /**
   * <p>Evaluates a binary expression, which consists of two expressions with an operator in the 
   * middle.
   * Eg. <code>a + b</code> or <code>a && b</code>.</p>
   * 
   * @param expression  The input expression
   * @return            Expression Type
   */
  protected EvaluatedData evaluateExpression(BinaryExpr expression) {

    Operator op = expression.getOperator();

    // BOOLEAN STATEMENTS
    if (op==Operator.AND || op==Operator.OR) {
      Type operandType=new Type("Z", true);

      // Evaluate Left
      evaluateExpression(expression.getLeft());
      JumpInstruction jump1;
      if (op==Operator.AND) {
        jump1 = code.addJumpInstruction(OpCode.ifeq); // Jump to false if left is false
      } else { //OR
        jump1 = code.addJumpInstruction(OpCode.ifne); // Jump to true if left is true
      }

      // Evaluate Right
      evaluateExpression(expression.getRight());
      JumpInstruction jump2 = code.addJumpInstruction(OpCode.ifeq); // Always jump to false if right is false

      // True and jump to End
      int trueIndex = code.addInstruction(OpCode.iconst_1);
      if (op==Operator.OR) jump1.setOffset(trueIndex);
      operandStack.addStackItem(operandType, code.getCurrentIndex());
      JumpInstruction jump3 = code.addJumpInstruction(OpCode.goto_); // Jump to end

      //False
      int falseIndex = code.addInstruction(OpCode.iconst_0);
      if (op==Operator.AND) jump1.setOffset(falseIndex);
      jump2.setOffset(falseIndex);

      //End
      jump3.setOffset(code.getCurrentIndex());

      return new EvaluatedData(operandType);
    
    // MATH STATEMENTS
    } else if (op==Operator.PLUS || op==Operator.MINUS) {
      Type operandType=new Type("I", true);

      evaluateExpression(expression.getLeft());
      evaluateExpression(expression.getRight());

      if (op==Operator.PLUS) {
        code.addInstruction(OpCode.iadd);
      } else if (op==Operator.MINUS) {
        code.addInstruction(OpCode.isub);
      }
      
      //operandStack.addStackItem(operandType, code.getCurrentIndex());
      return new EvaluatedData(operandType);

    // COMPARE STATEMENTS
    } else if (op==Operator.GREATER || 
        op==Operator.GREATER_EQUALS || 
        op==Operator.LESS || 
        op==Operator.LESS_EQUALS || 
        op==Operator.EQUALS ||
        op==Operator.NOT_EQUALS) {

      Type left=evaluateExpression(expression.getLeft()).getType();
      Type right=evaluateExpression(expression.getRight()).getType();

      //TODO
      if (left.isInt() && right.isInt()) {
        JumpInstruction jumpInstruction;
        Type operandType = new Type("I", true);
        
        if(op==Operator.GREATER) {
          jumpInstruction = code.addJumpInstruction(OpCode.if_icmple);
        } else if (op==Operator.GREATER_EQUALS) {
          jumpInstruction = code.addJumpInstruction(OpCode.if_icmplt);
        } else if (op==Operator.LESS) {
          jumpInstruction = code.addJumpInstruction(OpCode.if_icmpge);
        } else if (op==Operator.LESS_EQUALS) {
          jumpInstruction = code.addJumpInstruction(OpCode.if_icmpgt);
        } else if (op==Operator.EQUALS) {
          jumpInstruction = code.addJumpInstruction(OpCode.if_icmpne);
        } else {
          jumpInstruction = code.addJumpInstruction(OpCode.if_icmpeq);
        }

        return new EvaluatedData(operandType, jumpInstruction);
      }
    }
      
    //TODO
    return new EvaluatedData(new EmptyType());
  }

  /**
   * <p>Evaluates a boolean literal.
   * Eg. <code>true</code> or <code>false</code>.</p>
   * 
   * @param expression  The input expression
   * @return            Expression Type
   */
  protected EvaluatedData evaluateExpression(BooleanLiteralExpr expression) {
    boolean value = expression.getValue();

    if(value) {
      code.addInstruction(OpCode.iconst_1);
    } else {
      code.addInstruction(OpCode.iconst_0);
    }

    return new EvaluatedData(new Type("Z", true), value);
  }

  /**
   * <p>Evaluates a field access expression.
   * Eg. <code>System.out</code> or <code>myField1</code>.</p>
   * 
   * @param expression  The input expression
   * @return            Expression Type
   */
  protected EvaluatedData evaluateExpression(FieldAccessExpr expression) {
    // Get scope
    Type scope = evaluateExpression(expression.getScope()).getType();

    // Get type
    Type fieldType = new Type(expression.resolve().getType());

    // Add instruction
    code.addInstruction(OpCode.getstatic, 
      2, constantPool.addField_info(
        scope.getName(), 
        expression.getNameAsString(), 
        fieldType.getFormalName()
      )
    );

    return new EvaluatedData(fieldType);
  }

  /**
   * <p>Evaluates an integer literal expression.
   * Eg. <code>5</code>.</p>
   * 
   * @param expression  The input expression
   * @return            Expression Type
   */
  protected EvaluatedData evaluateExpression(IntegerLiteralExpr expression) {
    int value = expression.asNumber().intValue(); //Evaluate integer value of the expression

    if (value==0) {
      code.addInstruction(OpCode.iconst_0);
    } else if (value==1) {
      code.addInstruction(OpCode.iconst_1);
    } else if (value==2) {
      code.addInstruction(OpCode.iconst_2);
    } else if (value==3) {
      code.addInstruction(OpCode.iconst_3);
    } else if (value==4) {
      code.addInstruction(OpCode.iconst_4);
    } else if (value==5) {
      code.addInstruction(OpCode.iconst_5);
    } else {
      code.addInstruction(OpCode.bipush, 1, value);
    }

    code.addMaxStack();

    return new EvaluatedData(new Type("I", true), value);
  }

  /**
   * <p>Evaluates a method call expression.
   * Eg. <code>method1(arg1);</code> or <code>class.getThis();</code>.</p>
   * 
   * @param expression  The input expression
   * @return            Expression Type
   */
  protected EvaluatedData evaluateExpression(MethodCallExpr expression) {
    boolean isStatic =false;

    //Evaluate scope
    String scopeName;
    if(expression.getScope().isPresent()) {
      scopeName=evaluateExpression(expression.getScope().get()).getType().getName();
    } else {
      scopeName=className;
      isStatic=true; //Assume no scope=static method //TODO
    }

    //Evaluate arguments
    List<Type> argTypes = new ArrayList<>();
    expression.getArguments().forEach(arg -> 
      argTypes.add(evaluateExpression(arg).getType())
    );

    //Get method return type
    Type returnType = new Type(expression.resolve().getReturnType());

    //Determine if static or not
    OpCode op;
    if (isStatic) {
      op=OpCode.invokestatic;
    } else {
      op=OpCode.invokevirtual;
    }

    //Add instruction
    code.addInstruction(op,
      2, constantPool.addMethod_info(
        scopeName, 
        expression.getName().asString(),
        Util.createTypeInfo(returnType, argTypes)
      )
    );

    return new EvaluatedData(returnType);
  }

  /**
   * <p>Evaluates a name expression.
   * Eg. <code>java.lang.String</code> or <code>var1</code>.</p>
   * 
   * @param expression  The input expression
   * @return            Expression Type
   */
  protected EvaluatedData evaluateExpression(NameExpr expression) {
    //Get the type of the name expression
    Type exprType = new Type(expression.calculateResolvedType());

    //Search localVariableTable for variable name. Return type if it is not found
    int index = localVariables.find(expression.getNameAsString());
    if (index!=-1) {

      if (exprType.isInt() || exprType.isBool()) {
        OpCode op;
        if (index==0) {
          op=OpCode.iload_0;
        } else if (index==1) {
          op=OpCode.iload_1;
        } else if (index==2) {
          op=OpCode.iload_2;
        } else if (index==3) {
          op=OpCode.iload_3;
        } else {
          op=OpCode.nop; //TODO
        }
        code.addInstruction(op);
      }
    }

    return new EvaluatedData(exprType, expression.getNameAsString());
  }
  
  /**
   * <p>Evaluates a String literal expression.
   * Eg. <code>"Hello World!"</code>.</p>
   * 
   * @param expression  The input expression
   * @return            Expression Type
   */
  protected EvaluatedData evaluateExpression(StringLiteralExpr expression) {
    Type exprType=new Type("java/lang/String", false);

    code.addInstruction(OpCode.ldc,
      1, constantPool.addString_info(expression.asString())
    );
    code.addMaxStack();

    return new EvaluatedData(exprType, expression.asString());
  }

  /**
   * 
   * 
   * @param expression
   * @return
   */
  protected EvaluatedData evaluateExpression(UnaryExpr expression) {
    if (expression.getOperator()==UnaryExpr.Operator.MINUS) {
      int value = expression.getExpression().asIntegerLiteralExpr().asNumber().intValue();

      if(value==1) {
        code.addInstruction(OpCode.iconst_m1);
      } else {
        code.addInstruction(OpCode.bipush, 1, -value);
      }
      return new EvaluatedData(new Type("I", true), -value);
    }

    return new EvaluatedData(new EmptyType());
  }

  /**
   * <p>Evaluates a variable declaration. Can be a multitide of variable declarations.
   * Eg. <code>int a = 5;</code> or <code>String var1, var2;</code>.</p>
   * 
   * @param expression  The input expression
   * @return            Expression Type
   */
  protected EvaluatedData evaluateExpression(VariableDeclarationExpr expression) {

    Type exprType = new EmptyType();

    // Loop through variable declarations
    for (VariableDeclarator variableDeclaration : expression.getVariables()) {

      // Evaluate body of declaration if it exists
      variableDeclaration.getInitializer().ifPresent(body -> evaluateExpression(body));

      //Create type
      exprType = new Type(variableDeclaration.getType());

      // Add new variable to local variables table
      localVariables.add(variableDeclaration.getNameAsString(), exprType, code.getCurrentIndex());

      int index = localVariables.size()-1;
      if(exprType.isInt() || exprType.isBool()) {
        if (index==0) {
          code.addInstruction(OpCode.istore_0);
        } else if (index==1) {
          code.addInstruction(OpCode.istore_1);
        } else if (index==2) {
          code.addInstruction(OpCode.istore_2);
        } else if (index==3) {
          code.addInstruction(OpCode.istore_3);
        }
      }
    }

    return new EvaluatedData(exprType);
  }
}