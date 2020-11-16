package com.adamthorpe.javacompiler.Visitors;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;

public class MethodVisitor extends GenericListVisitorAdapter<MethodDeclaration,Void> {

  @Override
  public List<MethodDeclaration> visit(MethodDeclaration md, Void arg) {
    super.visit(md, arg);

    List<MethodDeclaration> list = new ArrayList<>();
    list.add(md);
    return list;
  }
  
}
