package com.adamthorpe.javacompiler;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.Types.Type;
// import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.resolution.types.ResolvedType;

public class Util {
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
  public static String createTypeInfo(Type returnType, List<Type> argTypes) {
    String string = "(";
    for(Type arg : argTypes) {
      string += arg.getFormalName();
    }
    return string + ")" + returnType.getFormalName();
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
  public static String createTypeInfo(Type returnType) {
    return createTypeInfo(returnType, new ArrayList<>());
  }
}
