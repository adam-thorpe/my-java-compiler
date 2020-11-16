package com.adamthorpe.javacompiler;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.ConstantPoolTypes.CONSTANT;
import com.adamthorpe.javacompiler.Visitors.ClassVisitor;
import com.adamthorpe.javacompiler.Visitors.MethodVisitor;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitor;

public class ClassFileCreator {

  private CompilationUnit cu;
  private List<CONSTANT> constantPool;

  public ClassFileCreator(CompilationUnit cu) {
    this.cu = cu;
    this.constantPool = new ArrayList<>();

  }

  protected void parse() {
    ClassOrInterfaceDeclaration classDeclaration = new ClassVisitor().visit(cu, null);
    List<MethodDeclaration> methodDeclarations = new MethodVisitor().visit(cu, null);

    createClassInfo(classDeclaration);
  }

  protected void createClassInfo(ClassOrInterfaceDeclaration classDeclaration) {
    String className = classDeclaration.getNameAsString();
    NodeList<ClassOrInterfaceType> superClassTypeList = classDeclaration.getExtendedTypes();
    
    // System.out.println(superClassTypeList.get(0));
  }

  protected void createMethodInfo() {

  }
}
