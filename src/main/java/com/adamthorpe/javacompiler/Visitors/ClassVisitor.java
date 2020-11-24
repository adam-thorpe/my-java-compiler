package com.adamthorpe.javacompiler.Visitors;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class ClassVisitor extends GenericVisitorAdapter<ClassOrInterfaceDeclaration, Void> {

  @Override
  public ClassOrInterfaceDeclaration visit(ClassOrInterfaceDeclaration cd, Void arg) {
    super.visit(cd, arg);

    return cd;
  }
  
}