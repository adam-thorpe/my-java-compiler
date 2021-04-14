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

public class TwoMethodsTest extends TestCase implements TestCaseInterface {

  public TwoMethodsTest() {
    super("bin/TwoMethods", "TwoMethods");
  }

  @BeforeClass
  public static void compileClass() {
    assertDoesNotThrow(() -> App.main(new String[] { "bin/TwoMethods/TwoMethods.java" }));
  }

  @Test
  public void testGeneralInfo() {
    testGeneralInfo("TwoMethods", Object.class, Modifier.PUBLIC);
  }

  @Test
  public void testConstructor() throws Exception {
    assertEquals(1, thisClass.getConstructors().length);
    testConstructor(thisClass.getConstructor(), Modifier.PUBLIC);
  }

  @Test
  public void testMethods() {
    assertEquals(2, thisClass.getDeclaredMethods().length);    
  }

  @Test
  public void testMethodA() throws Exception {
    Method method = thisClass.getMethod("A", int.class);
    testMethod(method, Modifier.PUBLIC, int.class);
    testMethodBody(method, new Object[]{5}, 5);
    testMethodBody(method, new Object[]{100}, 100);
    testMethodBody(method, new Object[]{-1}, -1);
  }

  @Test
  public void testMethodB() throws Exception {
    Method method = thisClass.getMethod("B");
    testMethod(method, Modifier.PUBLIC, String.class);
    testMethodBody(method, null, "Hello");
  }
}