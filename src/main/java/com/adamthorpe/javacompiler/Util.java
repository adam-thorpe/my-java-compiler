package com.adamthorpe.javacompiler;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.types.ResolvedType;

public class Util {

  /**
   * Creates a method description from type information
   * 
   * @param type Raw type
   * @return Resolved type
   */
  public static String resolveType(Type type) {
    if (type.isVoidType()) {
      return "V";
    } else if (type.isArrayType()) {
      ArrayType arrType = type.asArrayType();
      return "["+resolveType(arrType.getElementType());
    } else {
      return "L"+format(type.resolve().describe())+";";
    }
  }

  public static String generateType(ResolvedType type) {
    if (type.isVoid()) {
      return "V";
    } else {
      return "L"+format(type.describe())+";";
    }
  }

  public static String generateType(String type) {
    return "L"+type+";";
  }


  /**
   * Creates a well formed byte-code "type"
   * Eg. ()V
   *     (Ljava/lang/Object;)Ljava/lang/String
   * 
   * @param returnType the return type
   * @param argTypes optional argument types
   * @return type
   */
  public static String createTypeInfo(String returnType, List<String> argTypes) {
    String string = "(";
    for(String arg : argTypes) {
      string += arg;
    }
    return string + ")" + returnType;
  }

  public static String createTypeInfo(String returnType) {
    return createTypeInfo(returnType, new ArrayList<>());
  }


  public static String format(String input) {
    return input.replace('.', '/');
  }
  
}
