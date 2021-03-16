package com.adamthorpe.javacompiler.Visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

public class ClassVisitor extends GenericVisitorAdapter<ClassOrInterfaceDeclaration, Void> {

  @Override
  public ClassOrInterfaceDeclaration visit(ClassOrInterfaceDeclaration cd, Void arg) {
    super.visit(cd, arg);

    return cd;
  }
  
}