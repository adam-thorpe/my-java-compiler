package com.adamthorpe.javacompiler.TestCases;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Modifier;

import com.adamthorpe.javacompiler.App;
import com.adamthorpe.javacompiler.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test bin/BasicClass/BasicClass.java
 * Should compile the class to the same directory
 */
public class BasicClassTest extends TestCase {

  public BasicClassTest() {
    super("bin/BasicClass", "BasicClass");
  }

  @BeforeClass
  public static void compileClass() {
    assertDoesNotThrow(() -> App.main(new String[] { "bin/BasicClass/BasicClass.java" }));
  }

  @Test
  public void testGeneralInfo() {
    testGeneralInfo("BasicClass", Object.class, Modifier.PUBLIC);
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
  public void testMethodA() throws Exception {
    testMethod(thisClass.getMethod("A"), Modifier.PUBLIC, void.class);
  }
}