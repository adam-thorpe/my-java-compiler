package com.adamthorpe.javacompiler;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.resolution.types.ResolvedType;

public class Util {

  /**
   * <p>Converts a type into byte-code</p>
   * 
   * @param type raw type
   * @return byte-code type
   */
  public static String generateType(Type type) {
    if (type.isVoidType()) {
      return "V";
    } else if (type.isPrimitiveType() && type.asPrimitiveType().asString().equals("int")) {
      return "I";
    } else if (type.isArrayType()) {
      return "["+generateType(type.asArrayType().getElementType());
    } else {
      return "L"+formatReferenceType(type.resolve().describe())+";";
    }
  }

  /**
   * <p>Converts a resolved type into byte-code.</p>
   * 
   * @param type resolved type
   * @return byte-code type
   */
  public static String generateType(ResolvedType type) {
    if (type.isVoid()) {
      return "V";
    } else if (type.isPrimitive() && type.asPrimitive()==ResolvedPrimitiveType.INT) {
      return "I";
    } else if (type.isArray()) {
      return "["+generateType(type.asArrayType().getComponentType());
    } else {
      return "L"+formatReferenceType(type.describe())+";";
    }
  }

  /**
   * <p>Converts a type into byte-code. Assume this is a reference type</p>
   * 
   * @param type input type
   * @return byte-code type
   */
  public static String generateType(String type) {
    return "L"+type+";";
  }


  /**
   * <p>Creates a well formed byte-code description.</p>
   * 
   * Eg.
   * <code>()V</code>
   * <code>(Ljava/lang/Object;)Ljava/lang/String;</code>
   * 
   * @param returnType the return type
   * @param argTypes optional argument types
   * @return type description
   */
  public static String createTypeInfo(String returnType, List<String> argTypes) {
    String string = "(";
    for(String arg : argTypes) {
      string += arg;
    }
    return string + ")" + returnType;
  }

  /**
   * <p>Creates a well formed byte-code description where there are no arguments.</p>
   * 
   * Eg.
   * <code>()V</code>
   * <code>()Ljava/lang/String;</code>
   * 
   * @param returnType the return type
   * @return type description
   */
  public static String createTypeInfo(String returnType) {
    return createTypeInfo(returnType, new ArrayList<>());
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
  public static String formatReferenceType(String input) {
    return input.replace('.', '/');
  }
}
