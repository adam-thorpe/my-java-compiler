package com.adamthorpe.javacompiler.TestCases;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.adamthorpe.javacompiler.App;
import com.adamthorpe.javacompiler.ClassTest;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test bin/HelloWorld/HelloWorld.java
 */
public class HelloWorldTest extends ClassTest {

  public HelloWorldTest() {
    super("bin/HelloWorld", "HelloWorld");
  }

  @BeforeClass
  public static void compileClass() {
    assertDoesNotThrow(() -> App.main(new String[] { "bin/HelloWorld/HelloWorld.java" }));
  }

  @Test
  public void testGeneralInfo() {
    testGeneralInfo("HelloWorld", Object.class, Modifier.PUBLIC);
  }

  @Test
  public void testConstructor() {
    assertEquals(1, thisClass.getConstructors().length);

    assertDoesNotThrow(() -> 
      testConstructor(thisClass.getConstructor(), Modifier.PUBLIC)
    );
  }

  @Test
  public void testMethods() {
    assertEquals(1, thisClass.getDeclaredMethods().length);

    assertDoesNotThrow(() -> 
      testMethod(thisClass.getMethod("main", String[].class), Modifier.PUBLIC+Modifier.STATIC, void.class)
    );
  }

  @Test
  public void testOutputStream() throws Exception {
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    Object[] args = new Object[1];
    args[0] = new String[]{};
    thisClass.getMethod("main", String[].class).invoke(null, args);
    
    assertEquals("Hello World!", outStream.toString().trim());
  }
}