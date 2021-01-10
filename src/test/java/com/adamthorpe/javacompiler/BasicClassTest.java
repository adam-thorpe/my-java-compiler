package com.adamthorpe.javacompiler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Modifier;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test bin/BasicClass/BasicClass.java
 * Should compile the class to the same directory
 */
public class BasicClassTest extends ClassTest {

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
      testMethod(thisClass.getMethod("A"), Modifier.PUBLIC, void.class)
    );
  }
}