package com.adamthorpe.javacompiler;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.ConstantPoolTypes.CONSTANT;
import com.adamthorpe.javacompiler.ConstantPoolTypes.CONSTANT_Utf8_info;
import com.adamthorpe.javacompiler.Visitors.ClassVisitor;
import com.adamthorpe.javacompiler.Visitors.MethodVisitor;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

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
    createMethodInfo(methodDeclarations);
  }

  protected String resolveType(Type type) {
    if (type.isArrayType()) {
      return resolveType(type.asArrayType().getComponentType()) + "[]";
    } else if (type.isPrimitiveType()) {
      return type.asString();
    } else {
      return type.resolve().describe();
    }
  }

  protected void createClassInfo(ClassOrInterfaceDeclaration classDeclaration) {
    System.out.println("Class Name: " + classDeclaration.getName());
    // CONSTANT_Utf8_info()

    for (ClassOrInterfaceType superClassType : classDeclaration.getExtendedTypes()) {
      System.out.println("Super Class Name: " + resolveType(superClassType));
    }
  }

  protected void createMethodInfo(List<MethodDeclaration> methodDeclarations) {

    for (MethodDeclaration md : methodDeclarations) {
      System.out.println("Method name: " + md.getName());
      System.out.println("Method return type: " + resolveType(md.getType()));

      for (Parameter param : md.getParameters()) {
        System.out.println("Method param: " + param.getName());
        System.out.println("Method param type : " + resolveType(param.getType()));
      }

      // System.out.println(md.getBody().get().getStatements().get(0));

    }
  }

}
