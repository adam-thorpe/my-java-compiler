package com.adamthorpe.javacompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.adamthorpe.javacompiler.Types.Type;
import com.adamthorpe.javacompiler.Types.Code.ByteCode;
import com.adamthorpe.javacompiler.Types.Code.OpCode;
import com.adamthorpe.javacompiler.Types.Tables.ConstantPool;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

public class CodeGenerator {

  protected ConstantPool constantPool;
  protected ByteCode code;
  protected boolean hasReturn=false; //TEMP
  protected List<Parameter> params;

  public CodeGenerator(ConstantPool constantPool) {
    this.constantPool = constantPool;
  }

  public ByteCode run(BlockStmt block, List<Parameter> parameters) {
    code = new ByteCode(constantPool);
    params=parameters;

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
      //TODO
      return new Type("I", true);

    /**
     * Simple name
     * eg. java.lang.String
     *     variable1
     */
    } else if (expr.isNameExpr()) {
      Type exprType=new Type(expr.asNameExpr().calculateResolvedType());

      // Gather list of parameters to the method and see if they match the expression
      Optional<Parameter> p = params.stream().filter(
        param -> param.getName().toString().equals(expr.toNameExpr().get().toString())
      ).findFirst();

      //If they do, load integer constant 
      //TODO
      if (p.isPresent()) {
        code.addInstruction(OpCode.iload_1);
        code.addMaxLocals(1);
      }
      
      return exprType;

    /**
     * UNKNOWN
     */
    } else {
      System.out.println("NULL type generated");
      return new Type("NULL", true);
    }
  }

  protected void evaluateReturnStatement(Expression expression) {
    String returnTypeName = evaluateExpression(expression).getName();

    if (returnTypeName.equals("I")) {
      code.addInstruction(OpCode.ireturn);
    } else if (returnTypeName.equals("java/lang/String")) {
      code.addInstruction(OpCode.areturn);
    }
  }
  
}
