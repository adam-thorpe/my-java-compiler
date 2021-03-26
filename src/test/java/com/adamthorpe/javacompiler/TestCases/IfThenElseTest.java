package com.adamthorpe.javacompiler.TestCases;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.adamthorpe.javacompiler.App;
import com.adamthorpe.javacompiler.TestCase;
import com.adamthorpe.javacompiler.TestCaseInterface;

import org.junit.BeforeClass;
import org.junit.Test;

public class IfThenElseTest extends TestCase implements TestCaseInterface {

  public IfThenElseTest() {
    super("bin/IfThenElse", "IfThenElse");
  }

  @BeforeClass
  public static void compileClass() {
    assertDoesNotThrow(() -> App.main(new String[] { "bin/IfThenElse/IfThenElse.java" }));
  }

  @Test
  public void testGeneralInfo() {
    testGeneralInfo("IfThenElse", Object.class, Modifier.PUBLIC);
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
  public void testMethodIfTrue() throws Exception {
    Method method = thisClass.getMethod("ifTrue");
    testMethod(method, Modifier.PUBLIC, boolean.class);
    testMethodBody(method, null, true);
  }

  @Test
  public void testMethodIfElseString() throws Exception {
    Method method = thisClass.getMethod("ifElseString", boolean.class);
    testMethod(method, Modifier.PUBLIC, String.class);
    testMethodBody(method, new Object[]{true}, "Input was true");
    testMethodBody(method, new Object[]{false}, "Input was false");
  }

  @Test
  public void testMethodIfElseInt() throws Exception {
    Method method = thisClass.getMethod("ifElseInt", int.class);
    testMethod(method, Modifier.PUBLIC, String.class);
    testMethodBody(method, new Object[]{10}, "Input was greater than 5");
    testMethodBody(method, new Object[]{5}, "Input was 5");
    testMethodBody(method, new Object[]{0}, "Input was less than 5");
  }
}
