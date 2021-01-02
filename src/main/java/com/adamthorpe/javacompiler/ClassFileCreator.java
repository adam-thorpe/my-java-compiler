package com.adamthorpe.javacompiler;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.Types.Field_or_Method_info;
import com.adamthorpe.javacompiler.Types.Attributes.Attributes_info;
import com.adamthorpe.javacompiler.Types.Tables.AttributesTable;
import com.adamthorpe.javacompiler.Types.Tables.ConstantPool;
import com.adamthorpe.javacompiler.Types.Tables.FieldOrMethodTable;
import com.adamthorpe.javacompiler.Visitors.ClassVisitor;
import com.adamthorpe.javacompiler.Visitors.MethodVisitor;
import com.github.javaparser.ast.CompilationUnit;
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
  FieldOrMethodTable fieldsTable;
  FieldOrMethodTable methodsTable;
  AttributesTable attributesTable;

  public ClassFileCreator(CompilationUnit cu) {
    this.cu = cu;
    this.constantPool = new ConstantPool();

    this.fieldsTable = new FieldOrMethodTable(constantPool);
    this.methodsTable = new FieldOrMethodTable(constantPool);
    this.attributesTable = new AttributesTable(constantPool);
  }

  protected void parse() {
    ClassOrInterfaceDeclaration classDeclaration = new ClassVisitor().visit(cu, null);
    List<MethodDeclaration> methodDeclarations = new MethodVisitor().visit(cu, null);

    createClassInfo(classDeclaration);
    createMethodInfo(methodDeclarations);

    ClassFile classFile = new ClassFile(constantPool, thisClass, superClass, interfacesTable, fieldsTable, methodsTable, attributesTable);
    
    //print class file
    for (byte b : classFile.toByteArr()) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();
  }

  /**
   * Creates a method description from type information
   * @param type Raw type
   * @return Resolved type
   */
  protected String resolveType(Type type) {
    
    if (type.isPrimitiveType()) {
      switch(type.asString()) {
        case "void":
          return "()V";
        default:
          return type.asString(); //TEMP
      }
    } else if (type.isArrayType()) {
      return "[" + resolveType(type.asArrayType().getComponentType()); //WIP
    } else {
      return type.resolve().describe(); //TEMP
    }
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
    // Default Constructor
    methodsTable.insert("<init>", "()V", generateAttributes());

    for (MethodDeclaration md : methodDeclarations) {

      //WIP
      for (Parameter param : md.getParameters()) {
        System.out.println("Method param: " + param.getName());
        System.out.println("Method param type : " + resolveType(param.getType()));
      }


      //Generate attributes
      

      methodsTable.insert(md.getName().asString(), resolveType(md.getType()), generateAttributes());
    }
  }

  protected AttributesTable generateAttributes() {
    AttributesTable attributes = new AttributesTable(constantPool);
    return attributes;
  }

}
