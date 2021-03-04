package com.adamthorpe.javacompiler.TestCases;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Modifier;

import com.adamthorpe.javacompiler.App;
import com.adamthorpe.javacompiler.ClassTest;

import org.junit.BeforeClass;
import org.junit.Test;

public class BooleansTest extends ClassTest {

  public BooleansTest() {
    super("bin/Booleans", "Booleans");
  }

  @BeforeClass
  public static void compileClass() {
    assertDoesNotThrow(() -> App.main(new String[] { "bin/Booleans/Booleans.java" }));
  }

  @Test
  public void testGeneralInfo() {
    testGeneralInfo("Booleans", Object.class, Modifier.PUBLIC);
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
    assertEquals(6, thisClass.getDeclaredMethods().length);

    //Test method True
    assertDoesNotThrow(() ->
      testMethod(thisClass.getMethod("True"), Modifier.PUBLIC, boolean.class)
    );

    assertDoesNotThrow(() -> 
      assertEquals(true, thisClass.getMethod("True", boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {})
      ))
    );

    //Test method False
    assertDoesNotThrow(() ->
      testMethod(thisClass.getMethod("False"), Modifier.PUBLIC, boolean.class)
    );

    assertDoesNotThrow(() -> 
      assertEquals(false, thisClass.getMethod("False", boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {})
      ))
    );

    //Test method TestAND
    assertDoesNotThrow(() ->
      testMethod(thisClass.getMethod("TestAND"), Modifier.PUBLIC, boolean.class)
    );

    assertDoesNotThrow(() -> 
      assertEquals(false, thisClass.getMethod("TestAND").invoke(
        thisClass.getConstructor().newInstance(new Object[] {})
      ))
    );

    //Test method TestOR
    assertDoesNotThrow(() ->
      testMethod(thisClass.getMethod("TestOR"), Modifier.PUBLIC, boolean.class)
    );

    assertDoesNotThrow(() -> 
      assertEquals(true, thisClass.getMethod("TestOR").invoke(
        thisClass.getConstructor().newInstance(new Object[] {})
      ))
    );

    //Test method TestAND2
    assertDoesNotThrow(() ->
      testMethod(thisClass.getMethod("TestAND2", boolean.class, boolean.class), Modifier.PUBLIC, boolean.class)
    );

    assertDoesNotThrow(() -> 
      assertEquals(true, thisClass.getMethod("TestAND2", boolean.class, boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {}),
        true, true
      ))
    );

    assertDoesNotThrow(() -> 
      assertEquals(false, thisClass.getMethod("TestAND2", boolean.class, boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {}),
        true, false
      ))
    );

    assertDoesNotThrow(() -> 
      assertEquals(false, thisClass.getMethod("TestAND2", boolean.class, boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {}),
        false, true
      ))
    );

    assertDoesNotThrow(() -> 
      assertEquals(false, thisClass.getMethod("TestAND2", boolean.class, boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {}),
        false, false
      ))
    );

    //Test method TestOR2
    assertDoesNotThrow(() ->
      testMethod(thisClass.getMethod("TestOR2", boolean.class, boolean.class), Modifier.PUBLIC, boolean.class)
    );

    assertDoesNotThrow(() -> 
      assertEquals(true, thisClass.getMethod("TestOR2", boolean.class, boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {}),
        true, true
      ))
    );

    assertDoesNotThrow(() -> 
      assertEquals(true, thisClass.getMethod("TestOR2", boolean.class, boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {}),
        true, false
      ))
    );

    assertDoesNotThrow(() -> 
      assertEquals(true, thisClass.getMethod("TestOR2", boolean.class, boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {}),
        false, true
      ))
    );

    assertDoesNotThrow(() -> 
      assertEquals(false, thisClass.getMethod("TestOR2", boolean.class, boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {}),
        false, false
      ))
    );

  }
}
