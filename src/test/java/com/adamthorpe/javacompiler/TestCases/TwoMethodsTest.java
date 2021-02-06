package com.adamthorpe.javacompiler.TestCases;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Modifier;

import com.adamthorpe.javacompiler.App;
import com.adamthorpe.javacompiler.ClassTest;

import org.junit.BeforeClass;
import org.junit.Test;

public class TwoMethodsTest extends ClassTest {

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
  public void testConstructor() {
    assertEquals(1, thisClass.getConstructors().length);

    assertDoesNotThrow(() -> 
      testConstructor(thisClass.getConstructor(), Modifier.PUBLIC)
    );
  }

  @Test
  public void testMethods() {
    assertEquals(2, thisClass.getDeclaredMethods().length);

    // Test method A
    assertDoesNotThrow(() -> 
      testMethod(thisClass.getMethod("A", int.class), Modifier.PUBLIC, int.class)
    );

    int input =5;
    assertDoesNotThrow(() -> 
      assertEquals(input, thisClass.getMethod("A", int.class).invoke(thisClass, input))
    );

    // Test method B
    assertDoesNotThrow(() -> 
      testMethod(thisClass.getMethod("B"), Modifier.PUBLIC, String.class)
    );

    assertDoesNotThrow(() -> 
      assertEquals("Hello", thisClass.getMethod("B").invoke(thisClass))
    );
  }
}
