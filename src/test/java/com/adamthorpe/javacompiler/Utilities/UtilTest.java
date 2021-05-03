package com.adamthorpe.javacompiler.Utilities;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.adamthorpe.javacompiler.Compiler.Type;

import org.junit.Test;

public class UtilTest {

  static Type stringReferenceType;
  static Type objectArrReferenceType;
  static Type intType;
  static Type intArrType;
  static Type voidType;

  private Type[] setupTypes() {
    Type[] types = new Type[4];
    types[0] = new Type("I", true);
    types[1] = new Type("java/lang/String", false);
    types[2] = new Type("java/lang/Object", false, 1);
    types[3] = new Type("Z", true, 2);
    return types;
  }


  @Test
  public void testCreateTypeInfo() {
    Type[] types = setupTypes();
    
    // Test 1
    assertEquals("()I", Util.createTypeInfo(types[0]));

    // Test 2
    List<Type> args2 = new ArrayList<>();
    args2.add(types[2]);
    assertEquals("([Ljava/lang/Object;)Ljava/lang/String;", Util.createTypeInfo(types[1], args2));

    // Test 3
    List<Type> args3 = new ArrayList<>();
    args3.add(types[1]);
    args3.add(types[0]);
    assertEquals("(Ljava/lang/String;I)[[Z", Util.createTypeInfo(types[3], args3));
  }
}