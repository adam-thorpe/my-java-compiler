package com.adamthorpe.javacompiler.Types;

import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.resolution.types.ResolvedType;

public class Type {

  private boolean isPrimitive;
  private String name;
  private int arrayLevel=0;

  /**
   * <p>Constructor that builds a type from a JavaParser Type.</p>
   * 
   * @param typeInfo JavaParser Type info
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
      if (typeInfo.asPrimitiveType().asString().equals("int")) {
        name="I";
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
   * @param typeInfo JavaParser ResolvedType info
   */
  public Type(ResolvedType typeInfo) {
    //primitives
    if (typeInfo.isPrimitive()) {
      isPrimitive=true;
      //int
      if (typeInfo.asPrimitive()==ResolvedPrimitiveType.INT) {
        name="I";
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
   * @param typeInfo string type info
   * @param isPrimitive whether the given data is a primitive data type
   */
  public Type(String typeInfo, boolean isPrimitive) {
    this.isPrimitive=isPrimitive;
    this.name=typeInfo;
  }


  /**
   * <p>Constructor that builds a type from a string. The <code>isPrimitive</code> field indicates whether 
   * this is a primitive data type. Allows for array data types</p>
   * 
   * @param typeInfo string type info
   * @param isPrimitive whether the given data is a primitive data type
   * @param arrayLevel the number of array levels attached to this data type. Eg. int[][] = 2 array levels
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
   * @param input the input string
   * @return formatted string
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
   * @return name of the type
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
   * @return name of the type in byte-code
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

  public boolean isInt() {
    return name.equals("I") && isPrimitive;
  }
}
