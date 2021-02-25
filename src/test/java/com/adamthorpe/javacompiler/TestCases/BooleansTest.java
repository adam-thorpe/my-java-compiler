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
    assertEquals(7, thisClass.getDeclaredMethods().length);

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

    //Test method DoNotEvaluateRight
    assertDoesNotThrow(() ->
      testMethod(thisClass.getMethod("DoNotEvaluateRight"), Modifier.PUBLIC, boolean.class)
    );

    assertDoesNotThrow(() -> 
      assertEquals(true, thisClass.getMethod("DoNotEvaluateRight", boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {})
      ))
    );

    //Test method DoNotEvaluateRight2
    assertDoesNotThrow(() ->
      testMethod(thisClass.getMethod("DoNotEvaluateRight2"), Modifier.PUBLIC, boolean.class)
    );

    assertDoesNotThrow(() -> 
      assertEquals(false, thisClass.getMethod("DoNotEvaluateRight2", boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {})
      ))
    );

    //Test method EvaluateBoth
    assertDoesNotThrow(() ->
      testMethod(thisClass.getMethod("EvaluateBoth"), Modifier.PUBLIC, boolean.class)
    );

    assertDoesNotThrow(() -> 
      assertEquals(true, thisClass.getMethod("EvaluateBoth", boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {})
      ))
    );

    //Test method EvaluateBoth2
    assertDoesNotThrow(() ->
      testMethod(thisClass.getMethod("EvaluateBoth2"), Modifier.PUBLIC, boolean.class)
    );

    assertDoesNotThrow(() -> 
      assertEquals(false, thisClass.getMethod("EvaluateBoth2", boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {})
      ))
    );

    //Test method EvaluateNested
    assertDoesNotThrow(() ->
      testMethod(thisClass.getMethod("EvaluateNested"), Modifier.PUBLIC, boolean.class)
    );

    assertDoesNotThrow(() -> 
      assertEquals(true, thisClass.getMethod("EvaluateNested", boolean.class).invoke(
        thisClass.getConstructor().newInstance(new Object[] {})
      ))
    );
  }
  
}
