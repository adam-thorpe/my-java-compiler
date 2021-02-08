package com.adamthorpe.javacompiler;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.Types.Code.ByteCode;
import com.adamthorpe.javacompiler.Types.Code.Instruction;
import com.adamthorpe.javacompiler.Types.Code.OpCode;
import com.adamthorpe.javacompiler.Types.Tables.ConstantPool;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

public class CodeGenerator {

  protected ConstantPool constantPool;
  protected ByteCode code;

  public CodeGenerator(ConstantPool constantPool) {
    this.constantPool = constantPool;
  }

  public ByteCode run(BlockStmt block) {
    code = new ByteCode(constantPool);

    for (Statement statement : block.getStatements()) {

      if (statement.isExpressionStmt()) {
        evaluateExpression(statement.asExpressionStmt().getExpression());
      }
    }

    code.addInstruction(OpCode.return_);

    return code;
  }

  protected String evaluateExpression(Expression expr) {
    /**
     * Method call
     * eg. scope?.name(..args?) -> ret
     */
    if (expr.isMethodCallExpr()) {
      MethodCallExpr methodExpr = expr.asMethodCallExpr();

      // Get scope
      String scope = ""; //TEMP
      if(methodExpr.getScope().isPresent()) {
        scope = evaluateExpression(methodExpr.getScope().get());
      }

      // Get args
      List<String> args = new ArrayList<>();
      methodExpr.getArguments().forEach(arg -> 
        args.add(Util.generateType(evaluateExpression(arg)))
      );

      // Get return type
      String returnType = Util.generateType(methodExpr.resolve().getReturnType());

      code.addInstruction(OpCode.invokevirtual,
        2, constantPool.addMethod_info(
          scope, 
          methodExpr.getName().asString(),
          Util.createTypeInfo(returnType, args)
        )
      );

      return ""; //TO DO

    /**
     * Field access
     * eg.  type scope.name
     */
    } else if (expr.isFieldAccessExpr()) {
      FieldAccessExpr fieldExpr = expr.asFieldAccessExpr();

      String scope=evaluateExpression(fieldExpr.getScope());
      String fieldName=fieldExpr.getNameAsString();
      String fieldType=Util.generateType(fieldExpr.resolve().getType());

      code.addInstruction(OpCode.getstatic, 
        2, constantPool.addField_info(scope, fieldName, fieldType)
      );

      return Util.formatReferenceType(fieldExpr.resolve().getType().describe());

    /**
     * String literal
     * eg. "a"
     */
    } else if (expr.isStringLiteralExpr()) {
      String data = expr.toStringLiteralExpr().get().asString();

      code.addInstruction(OpCode.ldc,
        1, constantPool.addString_info(data)
      );

      return "java/lang/String";

    /**
     * Simple name
     * eg. java.lang.String
     *     variable1
     */
    } else if (expr.isNameExpr()) {
      return Util.formatReferenceType(expr.calculateResolvedType().describe());

    /**
     * Unknown
     */
    } else {
      System.out.println("not filtered yet: " + expr.toString());
      return "";
    }
  }
  
}
