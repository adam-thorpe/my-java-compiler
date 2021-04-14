package com.adamthorpe.javacompiler.Compiler;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.ClassFile.ClassFile;
import com.adamthorpe.javacompiler.ClassFile.Attributes.AttributesTable;
import com.adamthorpe.javacompiler.ClassFile.Code.ByteCode;
import com.adamthorpe.javacompiler.ClassFile.Code.OpCode;
import com.adamthorpe.javacompiler.ClassFile.ConstantPool.ConstantPool;
import com.adamthorpe.javacompiler.ClassFile.FieldsOrMethods.FieldOrMethodTable;
import com.adamthorpe.javacompiler.ClassFile.Interfaces.InterfaceTable;
import com.adamthorpe.javacompiler.Utilities.Util;
import com.adamthorpe.javacompiler.Visitors.ClassVisitor;
import com.adamthorpe.javacompiler.Visitors.MethodVisitor;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

/**
 * <p>The core of Java Compiler. This class does most of the organising and creating data
 * structures for all of the components.</p>
 */
public class CompilerCore {

  protected CompilationUnit cu;
  protected String className;
  protected String filePath;

  // Classfile data
  protected ConstantPool constantPool;
  protected int thisClassIndex;
  protected int superClassIndex;
  protected InterfaceTable interfacesTable;
  protected FieldOrMethodTable fieldsTable;
  protected FieldOrMethodTable methodsTable;
  protected AttributesTable attributesTable;

  /**
   * <p>Constructs the class</p>
   * 
   * @param cu        The <code>CompilationUnit</code> of JavaParser. This contains the syntax tree of our program
   * @param filePath  Path to our file. Used as the output destination
   */
  public CompilerCore(CompilationUnit cu, String filePath) {
    this.cu = cu;
    this.filePath = filePath;

    this.constantPool = new ConstantPool();
    this.interfacesTable = new InterfaceTable(constantPool);
    this.fieldsTable = new FieldOrMethodTable(constantPool);
    this.methodsTable = new FieldOrMethodTable(constantPool);
    this.attributesTable = new AttributesTable(constantPool);
  }

  /**
   * <p>Parses the syntax tree and creates our resulting classfile.</p> 
   */
  public void parse() {
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
   * <p>Creates information about the class being compiled.</p>
   * 
   * @param classDeclaration  Class data
   */
  protected void createClassInfo(ClassOrInterfaceDeclaration classDeclaration) {
    // Define this class
    className = classDeclaration.getNameAsString();
    thisClassIndex = constantPool.addClass_info(className);
    
    // Define the super class
    if (classDeclaration.getExtendedTypes().isEmpty()) {
      // Add default superclass "Object"
      superClassIndex = constantPool.addClass_info("java/lang/Object");
    }
  }

  /**
   * <p>Creates information for each declared and non-declared method.</p>
   * 
   * @param methodDeclarations  Method Declaration data
   */
  protected void createMethodInfo(List<MethodDeclaration> methodDeclarations) {

    createConstructor();
    boolean isStatic=false;

    for (MethodDeclaration md : methodDeclarations) {

      int accessFlags=0;
      // Check access flags
      if(md.isPublic()) accessFlags+=Modifier.PUBLIC;
      if(md.isStatic()) {
        accessFlags+=Modifier.STATIC;
        isStatic=true;
      }

      // Get list of parameters
      List<Type> paramTypes = new ArrayList<>();
      for (Parameter param : md.getParameters()) {
        paramTypes.add(new Type(param.getType()));
      }

      //Generate code in the code generator
      AttributesTable attributes = new CodeGenerator(constantPool, className, isStatic).run(md.getBody().get(), md.getParameters());

      //Add method to method table
      methodsTable.insert(md.getNameAsString(), accessFlags, Util.createTypeInfo(new Type(md.getType()), paramTypes), attributes);
    }
  }

  /**
   * <p>Creates information for the default non-declared constructor.</p>
   */
  protected void createConstructor() {
    //Generate code
    ByteCode code = new ByteCode();
    code.addInstruction(OpCode.aload_0);
    code.addInstruction(OpCode.invokespecial, 
      2, constantPool.addMethod_info("java/lang/Object", "<init>", "()V")
    );
    code.addInstruction(OpCode.return_);

    //Create code attribute
    AttributesTable attributes = new AttributesTable(constantPool);
    attributes.addCodeAttribute(code, new AttributesTable(constantPool));

    //Add method to method table
    methodsTable.insert("<init>", Modifier.PUBLIC,  "()V", attributes);
  }

  /**
   * <p>Creates information for the main attributes table.</p>
   */
  protected void createAttributes() {
    attributesTable.addSourceFileAttribute(className + ".java");
  }
}