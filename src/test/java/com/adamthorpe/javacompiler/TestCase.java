package com.adamthorpe.javacompiler;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Before;

public abstract class TestCase {

  protected String classPath;
  protected String className;
  protected Class<?> thisClass;

  public TestCase(String classPath, String className) {
    this.classPath=classPath;
    this.className=className;
	}

  /**
   * Loads the class file before each test
   * 
   * @throws Exception class file could not be loaded
   */
  @Before
  public void loadClass() throws Exception {

    try {
      File file = new File(classPath);
      URL url = file.toURI().toURL();

      URLClassLoader cl = new URLClassLoader(new URL[]{url});
      thisClass = cl.loadClass(className);
      cl.close();

    } catch (MalformedURLException | ClassNotFoundException e){
      throw new Exception(e.fillInStackTrace());
    }
  }

  /**
   * A very shallow test of a class's details
   * 
   * @param className       The expected class name
   * @param superclassType  The expected superclass type
   * @param accessModifer   The expected access modifier
   */
  public void testGeneralInfo(String className, Class<?> superclassType, int accessModifer) {
    assertEquals(className, thisClass.getName());
    assertEquals(superclassType, thisClass.getSuperclass());
    assertEquals(accessModifer, thisClass.getModifiers());
  }

  /**
   * A very shallow test of a constructor
   * 
   * @param constructor   The constructor
   * @param accessModifer The expected access modifier
   */
  public void testConstructor(Constructor<?> constructor, int accessModifer) {
    assertEquals(accessModifer, constructor.getModifiers());
  }

  /**
   * A very shallow test of a method's descriptor
   * 
   * @param method          The method
   * @param accessModifier  The expected access modifier
   * @param returnType      The expected return type
   */
  public void testMethod(Method method, int accessModifer, Class<?> returnType) {
    assertEquals(accessModifer, method.getModifiers());
    assertEquals(returnType, method.getReturnType());
  }

  /**
   * A test of a method's body and return value
   * 
   * @param method      The method
   * @param methodInit  Method inititaliser
   * @param expected    The expected return value
   */
  public void testMethodBody(Method method, Object[] methodInit, Object expected) {
    assertDoesNotThrow(() -> 
      assertEquals(expected, method.invoke(
        thisClass.getConstructor().newInstance(new Object[] {}),
        methodInit
      ))
    );
  }
}