package com.adamthorpe.javacompiler.TestCases;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Modifier;

import com.adamthorpe.javacompiler.App;
import com.adamthorpe.javacompiler.ClassTest;

import org.junit.BeforeClass;
import org.junit.Test;

public class AssignValueTest extends ClassTest {

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
  public void testConstructor() {
    assertEquals(1, thisClass.getConstructors().length);

    assertDoesNotThrow(() -> testConstructor(thisClass.getConstructor(), Modifier.PUBLIC));
  }

  @Test
  public void testMethods() {
    assertEquals(1, thisClass.getDeclaredMethods().length);

    // Test method A
    assertDoesNotThrow(() -> testMethod(thisClass.getMethod("addFunction", int.class), Modifier.PUBLIC, int.class));

    int input = 5;
    assertDoesNotThrow(() -> 
      assertEquals(10, thisClass.getMethod("addFunction", int.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {}), 
        input
      ))
    );
  }
}
