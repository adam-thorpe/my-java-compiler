package com.adamthorpe.javacompiler;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.Types.LocalVariable;
import com.adamthorpe.javacompiler.Types.Type;
import com.adamthorpe.javacompiler.Types.Code.ByteCode;
import com.adamthorpe.javacompiler.Types.Code.OpCode;
import com.adamthorpe.javacompiler.Types.Tables.ConstantPool;
import com.adamthorpe.javacompiler.Types.Tables.LocalVariableTable;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
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
    localVariables.add(new LocalVariable("this", "")); //TODO
    for (Parameter param : parameters) {
      localVariables.add(new LocalVariable(param.getNameAsString(), param.getTypeAsString()));
      code.addMaxLocals(1);
    }

    for (Statement statement : block.getStatements()) {
      if (statement.isExpressionStmt()) {
        evaluateExpression(statement.asExpressionStmt().getExpression());
      } else if (statement.isReturnStmt()) {
        hasReturn=true;
        statement.asReturnStmt().getExpression().ifPresentOrElse(
          expr -> evaluateReturnStatement(expr), 
          () -> code.addInstruction(OpCode.return_)
        );
      }
    }

    if (!hasReturn) code.addInstruction(OpCode.return_);
    return code;
  }

  /**
   * <p>This method will evaluate and break down a single expression into it's sub-expressions
   * and then evaluate those. At each level it will return the java type of that expression, 
   * whether that be the type of a field or the return type of the method.</p>
   * 
   * @param expr the given expression
   * @return string representation of the evaluated expression
   */
  protected Type evaluateExpression(Expression expr) {
    /**
     * Method call
     * eg. scope?.name(..args?) -> ret
     */
    if (expr.isMethodCallExpr()) {
      MethodCallExpr methodExpr = expr.asMethodCallExpr();

      // Get scope
      Type scope;
      if(methodExpr.getScope().isPresent()) {
        scope=evaluateExpression(methodExpr.getScope().get());
      } else {
        scope=new Type("", false); //TODO
      }

      // Get args
      List<Type> args = new ArrayList<>();
      methodExpr.getArguments().forEach(arg -> 
        args.add(evaluateExpression(arg))
      );

      // Get return type
      Type returnType = new Type(methodExpr.resolve().getReturnType());

      code.addInstruction(OpCode.invokevirtual,
        2, constantPool.addMethod_info(
          scope.getName(), 
          methodExpr.getName().asString(),
          Util.createTypeInfo(returnType, args)
        )
      );

      return returnType;

    /**
     * Field access
     * eg.  type scope.name
     */
    } else if (expr.isFieldAccessExpr()) {
      FieldAccessExpr fieldExpr = expr.asFieldAccessExpr();

      // Get scope
      Type scope = evaluateExpression(fieldExpr.getScope());

      // Get type
      Type fieldType = new Type(fieldExpr.resolve().getType());

      code.addInstruction(OpCode.getstatic, 
        2, constantPool.addField_info(
          scope.getName(), 
          fieldExpr.getNameAsString(), 
          fieldType.getFormalName()
        )
      );

      return fieldType;

    /**
     * Variable Expression
     * eg.  int var1;
     *      String var2 = "b";
     */
    } else if (expr.isVariableDeclarationExpr()) {
      VariableDeclarationExpr a = expr.toVariableDeclarationExpr().get();

      

      for (VariableDeclarator vd : a.getVariables()) {

        // Evaluate body of declaration
        if(vd.getInitializer().isPresent()) {
          evaluateExpression(vd.getInitializer().get());
        }

        // Add new var
        localVariables.add(new LocalVariable(vd.getName().asString(), vd.getType().asString()));
        code.addMaxLocals(1);

        int index = localVariables.size()-1; //TODO

        System.out.println(a);
        System.out.println(vd.getType().asString());
        System.out.println(index);

        if(vd.getType().asString().equals("int")) {
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

        //TODO
      }

      return new Type("", false);

    /**
     * Assignment
     * eg.  var1 = "a";
     *      var2 += 0;
     */
    } else if (expr.isAssignExpr()) {
      System.out.println("assign " + expr.toString());
      return new Type("", false);

    /**
     * String literal
     * eg. "a"
     */
    } else if (expr.isStringLiteralExpr()) {
      code.addInstruction(OpCode.ldc,
        1, constantPool.addString_info(expr.toStringLiteralExpr().get().asString())
      );
      code.addMaxStack(1);
      return new Type("java/lang/String", false);

    /**
     * Integer literal
     * eg. 1
     */
    } else if (expr.isIntegerLiteralExpr()) {
      int value = expr.toIntegerLiteralExpr().get().asNumber().intValue();

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
      }

      return new Type("I", true);

    /**
     * Binary Expression
     * eg.  a + b
     *      a && b
     */
    } else if (expr.isBinaryExpr()) {

      BinaryExpr be = expr.toBinaryExpr().get();
      Type left = evaluateExpression(be.getLeft());
      Type right = evaluateExpression(be.getRight());

      if (be.getOperator()==Operator.PLUS) { //TODO
        code.addInstruction(OpCode.iadd);
        code.addMaxStack(1);
      }

      return new Type("", false); //TODO

    /**
     * Simple name
     * eg. java.lang.String
     *     variable1
     */
    } else if (expr.isNameExpr()) {
      Type exprType=new Type(expr.asNameExpr().calculateResolvedType());

      int index = localVariables.find(expr.toNameExpr().get().toString());
      if (index==-1) return exprType;

      String type = localVariables.get(index).getType();

      if (type.equals("int")) {
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

    /**
     * UNKNOWN
     */
    } else {
      System.out.println("NULL type generated. " + expr.toString());
      return new Type("NULL", true);
    }
  }

  /**
   * 
   * 
   * @param expression
   */
  protected void evaluateReturnStatement(Expression expression) {
    String returnTypeName = evaluateExpression(expression).getName();

    if (returnTypeName.equals("I")) {
      code.addInstruction(OpCode.ireturn);
    } else if (returnTypeName.equals("java/lang/String")) {
      code.addInstruction(OpCode.areturn);
    }
  }
  
}
