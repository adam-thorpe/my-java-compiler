package com.adamthorpe.javacompiler.Types;

import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.resolution.types.ResolvedType;

public class Type {

  private boolean isPrimitive;
  private String name;
  private int arrayLevel=0;

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
      }

    //void
    } else if (typeInfo.isVoidType()) {
      isPrimitive=true;
      name="V";

    //reference
    } else if (typeInfo.isReferenceType()) {
      isPrimitive=false;
      name=format(typeInfo.resolve().describe());

    }
  }

  public Type(ResolvedType typeInfo) {
    //primitives
    if (typeInfo.isPrimitive()) {
      isPrimitive=true;
      //int
      if (typeInfo.asPrimitive()==ResolvedPrimitiveType.INT) {
        name="I";
      }

    //void
    } else if (typeInfo.isVoid()) {
      isPrimitive=true;
      name="V";

    //reference
    } else if (typeInfo.isReference()) {
      isPrimitive=false;
      name=format(typeInfo.describe());
    }
  }
  
  public Type(String typeInfo, boolean isPrimitive) {
    this.isPrimitive=isPrimitive;
    this.name=typeInfo;
  }

  public Type(String typeInfo, boolean isPrimitive, int arrayLevel) {
    this(typeInfo, isPrimitive);
    this.arrayLevel=arrayLevel;
  }

  protected String format(String input) {
    return input.replace('.', '/');
  }

  public String getName() {
    return name;
  }

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
  
}
