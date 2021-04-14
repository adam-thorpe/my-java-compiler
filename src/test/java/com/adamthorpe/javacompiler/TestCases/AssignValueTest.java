package com.adamthorpe.javacompiler.TestCases;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.adamthorpe.javacompiler.App;
import com.adamthorpe.javacompiler.TestCase;
import com.adamthorpe.javacompiler.TestCaseInterface;

import org.junit.BeforeClass;
import org.junit.Test;

public class AssignValueTest extends TestCase implements TestCaseInterface {

  public AssignValueTest() {
    super("bin/AssignValue", "AssignValue");
  }

  @BeforeClass
  public static void compileClass() {
    assertDoesNotThrow(() -> App.main(new String[] { "bin/AssignValue/AssignValue.java" }));
  }

  @Test
  public void testGeneralInfo() {
    testGeneralInfo("AssignValue", Object.class, Modifier.PUBLIC);
  }

  @Test
  public void testConstructor() throws Exception {
    assertEquals(1, thisClass.getConstructors().length);
    testConstructor(thisClass.getConstructor(), Modifier.PUBLIC);
  }

  @Test
  public void testMethods() {
    assertEquals(1, thisClass.getDeclaredMethods().length);
  }

  @Test
  public void testMethodAddFunction() throws Exception {
    Method method = thisClass.getMethod("addFunction", int.class);
    testMethod(method, Modifier.PUBLIC, int.class);
    testMethodBody(method, new Object[]{5}, 10);
    testMethodBody(method, new Object[]{100}, 105);
  }
}
