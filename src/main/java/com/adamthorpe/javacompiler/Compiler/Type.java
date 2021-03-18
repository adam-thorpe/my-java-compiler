package com.adamthorpe.javacompiler.Compiler;

import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.resolution.types.ResolvedType;

public class Type {

  private boolean isPrimitive;
  private String name;
  private int arrayLevel=0;

  /**
   * <p>Constructor that builds a type from a JavaParser Type.</p>
   * 
   * @param typeInfo  JavaParser Type info
   */
  public Type(com.github.javaparser.ast.type.Type typeInfo) {
    //calculate array sublevels
    while(typeInfo.isArrayType()) {
      arrayLevel++;
      typeInfo=typeInfo.asArrayType().getElementType();
    }

    //primitives
    if (typeInfo.isPrimitiveType()) {
      isPrimitive=true;
      //int
      if (typeInfo.asPrimitiveType().equals(PrimitiveType.intType())) {
        name="I";
      } else if (typeInfo.asPrimitiveType().equals(PrimitiveType.booleanType())) {
        name="Z";
      } else {
        name="";
      }

    //void
    } else if (typeInfo.isVoidType()) {
      isPrimitive=true;
      name="V";

    //reference
    } else if (typeInfo.isReferenceType()) {
      isPrimitive=false;
      name=format(typeInfo.resolve().describe());

    } else {
      isPrimitive=false;
      name="";
    }
  }

  /**
   * <p>Constructor that builds a type from a JavaParser ResolvedType.</p>
   * 
   * @param typeInfo  JavaParser ResolvedType info
   */
  public Type(ResolvedType typeInfo) {
    //primitives
    if (typeInfo.isPrimitive()) {
      isPrimitive=true;
      //int
      if (typeInfo.asPrimitive()==ResolvedPrimitiveType.INT) {
        name="I";
      } else if (typeInfo.asPrimitive()==ResolvedPrimitiveType.BOOLEAN) {
        name="Z";
      } else {
        name="";
      }

    //void
    } else if (typeInfo.isVoid()) {
      isPrimitive=true;
      name="V";

    //reference
    } else if (typeInfo.isReference()) {
      isPrimitive=false;
      name=format(typeInfo.describe());
      
    } else {
      isPrimitive=false;
      name="";
    }
  }
  
  /**
   * <p>Constructor that builds a type from a string. The <code>isPrimitive</code> field indicates whether 
   * this is a primitive data type.</p>
   * 
   * @param typeInfo    String type info
   * @param isPrimitive Whether the given data is a primitive data type
   */
  public Type(String typeInfo, boolean isPrimitive) {
    this.isPrimitive=isPrimitive;
    this.name=typeInfo;
  }


  /**
   * <p>Constructor that builds a type from a string. The <code>isPrimitive</code> field indicates whether 
   * this is a primitive data type. Allows for array data types</p>
   * 
   * @param typeInfo    String type info
   * @param isPrimitive Whether the given data is a primitive data type
   * @param arrayLevel  The number of array levels attached to this data type. Eg. int[][] = 2 array levels
   */
  public Type(String typeInfo, boolean isPrimitive, int arrayLevel) {
    this(typeInfo, isPrimitive);
    this.arrayLevel=arrayLevel;
  }

  /**
   * <p>Formats a reference type by replacing dots with slashes.</p>
   * 
   * Eg.
   * <code>java.lang.String</code> -> <code>java/lang/String</code>
   * 
   * @param input The input string
   * @return      Formatted string
   */
  protected String format(String input) {
    return input.replace('.', '/');
  }

  /**
   * <p>Returns the name of the type.</p>
   * 
   * Eg.
   * <code>java/lang/String</code>
   * 
   * @return  Name of the type
   */
  public String getName() {
    return name;
  }

  /**
   * <p>Returns the name of the type with Java byte code annotations.</p>
   * 
   * Eg.
   * <code>Ljava/lang/String;</code>
   * or <code>I</code>
   * 
   * @return  Name of the type in byte-code
   */
  public String getFormalName() {
    String arrayChars="";
    for(int i=0; i<arrayLevel; i++) {
      arrayChars+="[";
    }

    if(isPrimitive) {
      return arrayChars+name;
    } else {
      return arrayChars+"L"+name+";";
    }
  }

  /**
   * <p>Check if this is the primitive integer type.</p>
   * 
   * @return  <code>true</code> if this is an integer type else <code>false</code>
   */
  public boolean isInt() {
    return name.equals("I") && isPrimitive;
  }

  /**
   * <p>Check if this is the primitive boolean type.</p>
   * 
   * @return  <code>true</code> if this is a boolean type else <code>false</code>
   */
  public boolean isBool() {
    return name.equals("Z") && isPrimitive;
  }
}