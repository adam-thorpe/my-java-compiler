package com.adamthorpe.javacompiler.Utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.Compiler.Type;

public class Util {
  /**
   * <p>Creates a well formed byte-code description.</p>
   * 
   * Eg.
   * <code>()V</code>
   * <code>(Ljava/lang/Object;)Ljava/lang/String;</code>
   * 
   * @param returnType  The return type
   * @param argTypes    Optional argument types
   * @return            Type description
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
   * @param returnType  The return type
   * @return            Type description
   */
  public static String createTypeInfo(Type returnType) {
    return createTypeInfo(returnType, new ArrayList<>());
  }

  /**
   * <p>Writes an array of bytes to file.<p>
   * 
   * @param data  An array of bytes
   */
  public static void outputToFile(byte[] data, String destination) {
    try {
      OutputStream out = new FileOutputStream(destination);
      out.write(data);
      out.close();
      System.out.println("Class compiled successfully to " + destination);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}