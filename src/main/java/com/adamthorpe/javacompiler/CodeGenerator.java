package com.adamthorpe.javacompiler;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.Types.EmptyType;
import com.adamthorpe.javacompiler.Types.LocalVariable;
import com.adamthorpe.javacompiler.Types.Type;
import com.adamthorpe.javacompiler.Types.Code.ByteCode;
import com.adamthorpe.javacompiler.Types.Code.OpCode;
import com.adamthorpe.javacompiler.Types.Tables.ConstantPool;
import com.adamthorpe.javacompiler.Types.Tables.LocalVariableTable;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

public class CodeGenerator {

  protected ConstantPool constantPool;
  protected ByteCode code;
  protected boolean hasReturn=false; //TEMP
  protected LocalVariableTable localVariables;

  public CodeGenerator(ConstantPool constantPool) {
    this.constantPool = constantPool;
    this.localVariables = new LocalVariableTable();
  }

  /**
   * <p>Begins to start generating code from a method block.</p>
   * 
   * @param block the block of code to work on
   * @param parameters method parameters
   * @return resulting byte code
   */
  public ByteCode run(BlockStmt block, List<Parameter> parameters) {
    code = new ByteCode(constantPool);

    //Populate LocalVariableTable
    localVariables.add(new LocalVariable("this", new EmptyType())); //TODO
    for (Parameter param : parameters) {
      localVariables.add(new LocalVariable(param.getNameAsString(), new Type(param.getType())));
      code.addMaxLocals(1);
    }

    //Evaluate each statement
    for (Statement statement : block.getStatements()) {
      if (statement.isExpressionStmt()) {
        evaluateExpression(statement.asExpressionStmt().getExpression());

      } else if (statement.isReturnStmt()) { //Evaluate the return statement
        hasReturn=true;
        statement.asReturnStmt().getExpression().ifPresentOrElse(
          retExpr -> evaluateReturnStatement(retExpr), 
          () -> code.addInstruction(OpCode.return_)
        );
      }
    }

    if (!hasReturn) code.addInstruction(OpCode.return_);
    return code;
  }

  /**
   * <p>This method will determine the form of <code>expression</code> and then evaluate it further. This 
   * is a recursive function, as each expression often comprises of sub-expressions.</p>
   * 
   * @param expression the input expression
   * @return expression Type
   */
  protected Type evaluateExpression(Expression expression) {
    if (expression.isBinaryExpr()) {
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

    } else if (expression.isVariableDeclarationExpr()) {
      return evaluateExpression(expression.asVariableDeclarationExpr());

    } else {
      System.err.println("Unsupported expression: " + expression.toString());
      return new EmptyType();
    }
  }

  /**
   * <p>Evaluates a binary expression, which consists of two expressions with an operator in the 
   * middle.
   * Eg. <code>a + b</code> or <code>a && b</code>.</p>
   * 
   * @param expression the input expression
   * @return expression Type
   */
  protected Type evaluateExpression(BinaryExpr expression) {

    //Evaluate both sides of the expression
    Type left = evaluateExpression(expression.getLeft());
    Type right = evaluateExpression(expression.getRight());

    //Evaluate operator
    if (expression.getOperator()==Operator.PLUS) { //TODO
      code.addInstruction(OpCode.iadd);
      code.addMaxStack(1);
      return new Type("I", true); //Return integer type
    }

    return new EmptyType(); //TODO
  }

  /**
   * <p>Evaluates a boolean literal.
   * Eg. <code>true</code> or <code>false</code>.</p>
   * 
   * @param expression the input expression
   * @return expression Type
   */
  protected Type evaluateExpression(BooleanLiteralExpr expression) {
    boolean value = expression.getValue();

    if(value) {
      code.addInstruction(OpCode.iconst_1);
    } else {
      code.addInstruction(OpCode.iconst_0);
    }

    return new Type("Z", true);
  }

  /**
   * <p>Evaluates a field access expression.
   * Eg. <code>System.out</code> or <code>myField1</code>.</p>
   * 
   * @param expression the input expression
   * @return expression Type
   */
  protected Type evaluateExpression(FieldAccessExpr expression) {
    // Get scope
    Type scope = evaluateExpression(expression.getScope());

    // Get type
    Type fieldType = new Type(expression.resolve().getType());

    code.addInstruction(OpCode.getstatic, 
      2, constantPool.addField_info(
        scope.getName(), 
        expression.getNameAsString(), 
        fieldType.getFormalName()
      )
    );

    return fieldType;
  }

  /**
   * <p>Evaluates an integer literal expression.
   * Eg. <code>5</code>.</p>
   * 
   * @param expression the input expression
   * @return expression Type
   */
  protected Type evaluateExpression(IntegerLiteralExpr expression) {
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
      //TODO
    }

    return new Type("I", true);
  }

  /**
   * <p>Evaluates a method call expression.
   * Eg. <code>method1(arg1);</code> or <code>class.getThis();</code>.</p>
   * 
   * @param expression
   * @return
   */
  protected Type evaluateExpression(MethodCallExpr expression) {
    //Evaluate scope
    Type scopeType;
    if(expression.getScope().isPresent()) {
      scopeType=evaluateExpression(expression.getScope().get());
    } else {
      scopeType=new EmptyType(); //TODO
    }

    //Evaluate arguments
    List<Type> argTypes = new ArrayList<>();
    expression.getArguments().forEach(arg -> 
      argTypes.add(evaluateExpression(arg))
    );

    //Get method return type
    Type returnType = new Type(expression.resolve().getReturnType());

    code.addInstruction(OpCode.invokevirtual,
      2, constantPool.addMethod_info(
        scopeType.getName(), 
        expression.getName().asString(),
        Util.createTypeInfo(returnType, argTypes)
      )
    );

    return returnType;
  }

  /**
   * <p>Evaluates a name expression.
   * Eg. <code>java.lang.String</code> or <code>var1</code>.</p>
   * 
   * @param expression the input expression
   * @return expression Type
   */
  protected Type evaluateExpression(NameExpr expression) {
    //Get the type of the name expression
    Type exprType = new Type(expression.calculateResolvedType());

    //Search localVariableTable for variable name. Return type if it is not found
    int index = localVariables.find(expression.getNameAsString());
    if (index==-1) return exprType;

    if (exprType.isInt() || exprType.isBool()) {
      if (index==0) {
        code.addInstruction(OpCode.iload_0);
      } else if (index==1) {
        code.addInstruction(OpCode.iload_1);
      } else if (index==2) {
        code.addInstruction(OpCode.iload_2);
      } else if (index==3) {
        code.addInstruction(OpCode.iload_3);
      } else {
        code.addInstruction(OpCode.iload, 1, index);
      }
    }

    return exprType;
  }
  
  /**
   * <p>Evaluates a String literal expression.
   * Eg. <code>"Hello World!"</code>.</p>
   * 
   * @param expression the input expression
   * @return expression Type
   */
  protected Type evaluateExpression(StringLiteralExpr expression) {
    code.addInstruction(OpCode.ldc,
      1, constantPool.addString_info(expression.asString())
    );
    code.addMaxStack(1);

    return new Type("java/lang/String", false); //Return String Type
  }

  /**
   * <p>Evaluates a variable declaration. Can be a multitide of variable declarations.
   * Eg. <code>int a = 5;</code> or <code>String var1, var2;</code>.</p>
   * 
   * @param expression the input expression
   * @return expression Type
   */
  protected Type evaluateExpression(VariableDeclarationExpr expression) {

    Type expressionType = new EmptyType();

    // Loop through variable declarations
    for (VariableDeclarator variableDeclaration : expression.getVariables()) {

      // Evaluate body of declaration if it exists
      variableDeclaration.getInitializer().ifPresent(body -> evaluateExpression(body));

      //Create type
      expressionType = new Type(variableDeclaration.getType());

      // Add new variable to local variables table
      localVariables.add(
        new LocalVariable(variableDeclaration.getName().asString(), expressionType)
      );
      code.addMaxLocals(1);

      int index = localVariables.size()-1;
      if(expressionType.isInt() || expressionType.isBool()) {
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

    return expressionType;
  }

  /**
   * <p>Evaluates the expression in a return statement
   * Eg. <code>return var1;</code> or <code>return 2+3;</code>.</p>
   * 
   * @param expression input return expression
   */
  protected void evaluateReturnStatement(Expression expression) {
    Type returnType = evaluateExpression(expression);

    if (returnType.isInt() || returnType.isBool()) {
      code.addInstruction(OpCode.ireturn);
    } else if (returnType.getName().equals("java/lang/String")) {
      code.addInstruction(OpCode.areturn);
    }
  }
  
}
