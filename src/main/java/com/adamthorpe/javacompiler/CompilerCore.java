package com.adamthorpe.javacompiler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.Types.Type;
import com.adamthorpe.javacompiler.Types.Code.ByteCode;
import com.adamthorpe.javacompiler.Types.Code.OpCode;
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

public class CompilerCore {

  private CompilationUnit cu;
  private ConstantPool constantPool;
  private int thisClassIndex;
  private int superClassIndex;
  private String className;
  private String filePath;

  byte[] interfacesTable = new byte[0]; //TODO
  FieldOrMethodTable fieldsTable;
  FieldOrMethodTable methodsTable;
  AttributesTable attributesTable;

  public CompilerCore(CompilationUnit cu, String filePath) {
    this.cu = cu;
    this.constantPool = new ConstantPool();
    this.filePath = filePath;

    this.fieldsTable = new FieldOrMethodTable(constantPool);
    this.methodsTable = new FieldOrMethodTable(constantPool);
    this.attributesTable = new AttributesTable(constantPool);
  }

  protected void parse() {
    ClassOrInterfaceDeclaration classDeclaration = new ClassVisitor().visit(cu, null);
    List<MethodDeclaration> methodDeclarations = new MethodVisitor().visit(cu, null);

    createClassInfo(classDeclaration);
    createMethodInfo(methodDeclarations);
    createAttributes();

    // Create the class file
    try {
      ClassFile classFile = new ClassFile(constantPool, thisClassIndex, superClassIndex, interfacesTable, fieldsTable,
          methodsTable, attributesTable);

      // Write out to file
      Util.outputToFile(classFile.getData(), filePath + "/" + className + ".class");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * <p>Creates the data for the class.</p>
   * 
   * @param classDeclaration info about the class
   */
  protected void createClassInfo(ClassOrInterfaceDeclaration classDeclaration) {
    // Define this class
    className = classDeclaration.getNameAsString();
    thisClassIndex = constantPool.addClass_info(className);
    
    if (classDeclaration.getExtendedTypes().isEmpty()) {
      // Add default superclass "Object"
      superClassIndex = constantPool.addClass_info("java/lang/Object");
    } else {
      //Superclasses
      for (ClassOrInterfaceType superClassType : classDeclaration.getExtendedTypes()) {
        // TODO
      }
    }
  }

  /**
   * <p>Creates the data for each declared method and any default constructors.</p>
   * 
   * @param methodDeclarations the method declarations
   */
  protected void createMethodInfo(List<MethodDeclaration> methodDeclarations) {

    createConstructor();

    for (MethodDeclaration md : methodDeclarations) {

      int accessFlags=0;
      // Check access flags
      if(md.isPublic()) accessFlags+=Modifier.PUBLIC;
      if(md.isStatic()) accessFlags+=Modifier.STATIC;

      // Get list of parameters
      List<Type> paramTypes = new ArrayList<>();
      for (Parameter param : md.getParameters()) {
        paramTypes.add(new Type(param.getType()));
      }

      //Generate code in the code generator
      ByteCode code = new CodeGenerator(constantPool).run(md.getBody().get(), md.getParameters());

      //Create code attribute
      AttributesTable attributes = new AttributesTable(constantPool);
      attributes.addCodeAttribute(code);

      //Add method to method table
      methodsTable.insert(md.getNameAsString(), accessFlags, Util.createTypeInfo(new Type(md.getType()), paramTypes), attributes);
    }
  }

  /**
   * <p>Creates the data for a default constructor.</p>
   */
  protected void createConstructor() {
    //Generate code
    ByteCode code = new ByteCode(constantPool);
    code.addInstruction(OpCode.aload_0);
    code.addInstruction(OpCode.invokespecial, 
      2, constantPool.addMethod_info("java/lang/Object", "<init>", "()V")
    );
    code.addInstruction(OpCode.return_);

    //Create code attribute
    AttributesTable attributes = new AttributesTable(constantPool);
    attributes.addCodeAttribute(code);

    //Add method to method table
    methodsTable.insert("<init>", Modifier.PUBLIC,  "()V", attributes);
  }

  /**
   * <p>Creates the data for the main attributes table.</p>
   */
  protected void createAttributes() {
    attributesTable.addSourceFileAttribute(className + ".java");
  }
}
