package com.adamthorpe.javacompiler;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.ConstantPoolTypes.CONSTANT;
import com.adamthorpe.javacompiler.ConstantPoolTypes.CONSTANT_Class_info;
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

public class ClassFileCreator {

  private CompilationUnit cu;
  private ConstantPool constantPool;
  private int thisClass;
  private int superClass;
  
  byte[] interfacesTable = new byte[0];
  byte[] fieldsTable = new byte[0];
  byte[] methodsTable = new byte [0];
  byte[] attributesTable = new byte[0];

  public ClassFileCreator(CompilationUnit cu) {
    this.cu = cu;
    this.constantPool = new ConstantPool();
  }

  protected void parse() {
    ClassOrInterfaceDeclaration classDeclaration = new ClassVisitor().visit(cu, null);
    List<MethodDeclaration> methodDeclarations = new MethodVisitor().visit(cu, null);

    createInitMethodInfo();
    createClassInfo(classDeclaration);
    createMethodInfo(methodDeclarations);

    ClassFile classFile = new ClassFile(constantPool, thisClass, superClass, interfacesTable, fieldsTable, methodsTable, attributesTable);
    
    //print class file
    for (byte b : classFile.toByteArr()) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
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

  protected void createInitMethodInfo() {
    //Default Method
    constantPool.addMethod_info("java/lang/Object", "<init>", "()V");
  }

  protected void createClassInfo(ClassOrInterfaceDeclaration classDeclaration) {

    // Define this class
    thisClass = constantPool.addClass_info(classDeclaration.getNameAsString());

    if (classDeclaration.getExtendedTypes().isEmpty()) {
      // Add default superclass "Object"
      superClass = constantPool.addClass_info("java/lang/Object");
    } else {
      for (ClassOrInterfaceType superClassType : classDeclaration.getExtendedTypes()) {
        System.out.println("Super Class Name: " + resolveType(superClassType));

        // TO DO
      }
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
