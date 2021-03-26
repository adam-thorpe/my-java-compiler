package com.adamthorpe.javacompiler.TestCases;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.adamthorpe.javacompiler.App;
import com.adamthorpe.javacompiler.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

public class BooleansTest extends TestCase {

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
  public void testConstructor() throws Exception {
    assertEquals(1, thisClass.getConstructors().length);
    testConstructor(thisClass.getConstructor(), Modifier.PUBLIC);
  }

  @Test
  public void testMethods() {
    assertEquals(6, thisClass.getDeclaredMethods().length);
  }

  @Test
  public void testMethodTrue() throws Exception {
    Method m = thisClass.getMethod("True");
    testMethod(m, Modifier.PUBLIC, boolean.class);
    testMethodBody(m, null, true);
  }

  @Test
  public void testMethodFalse() throws Exception {
    Method m = thisClass.getMethod("False");
    testMethod(m, Modifier.PUBLIC, boolean.class);
    testMethodBody(m, null, false);
  }

  @Test
  public void testMethodTestAND() throws Exception {
     Method m = thisClass.getMethod("TestAND");
     testMethod(m, Modifier.PUBLIC, boolean.class);
     testMethodBody(m, null, false);
  }

  @Test
  public void testMethodTestOR() throws Exception {
    Method m = thisClass.getMethod("TestOR");
    testMethod(m, Modifier.PUBLIC, boolean.class);
    testMethodBody(m, null, true);
  }

  @Test
  public void testMethodTestAND2() throws Exception {
     Method m = thisClass.getMethod("TestAND2", boolean.class, boolean.class);
     testMethod(m, Modifier.PUBLIC, boolean.class);
     testMethodBody(m, new Object[]{true, true}, true);
     testMethodBody(m, new Object[]{true, false}, false);
     testMethodBody(m, new Object[]{false, true}, false);
     testMethodBody(m, new Object[]{false, false}, false);
  }

  @Test
  public void testMethodTestOR2() throws Exception {
     Method m = thisClass.getMethod("TestOR2", boolean.class, boolean.class);
     testMethod(m, Modifier.PUBLIC, boolean.class);
     testMethodBody(m, new Object[]{true, true}, true);
     testMethodBody(m, new Object[]{true, false}, true);
     testMethodBody(m, new Object[]{false, true}, true);
     testMethodBody(m, new Object[]{false, false}, false);
  }
}