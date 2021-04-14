package com.adamthorpe.javacompiler.TestCases;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.adamthorpe.javacompiler.App;
import com.adamthorpe.javacompiler.TestCase;
import com.adamthorpe.javacompiler.TestCaseInterface;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test bin/SimpleWhileLoop/SimpleWhileLoop.java
 */
public class SimpleWhileLoopTest extends TestCase implements TestCaseInterface {

  public SimpleWhileLoopTest() {
    super("bin/SimpleWhileLoop", "SimpleWhileLoop");
  }

  @BeforeClass
  public static void compileClass() {
    assertDoesNotThrow(() -> App.main(new String[] { "bin/SimpleWhileLoop/SimpleWhileLoop.java" }));
  }

  @Test
  public void testGeneralInfo() {
    testGeneralInfo("SimpleWhileLoop", Object.class, Modifier.PUBLIC);
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
  public void testMethodMain() throws Exception {
    Method method = thisClass.getMethod("main", String[].class);
    testMethod(method, Modifier.PUBLIC+Modifier.STATIC, void.class);

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    Object[] args = new Object[1];
    args[0] = new String[]{};
    method.invoke(null, args);
    
    assertEquals("0\n1\n2\n3\n4", outStream.toString().trim());
  }
}