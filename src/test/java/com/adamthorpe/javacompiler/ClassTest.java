package com.adamthorpe.javacompiler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Before;

public abstract class ClassTest {

  protected String classPath;
  protected String className;
  protected Class<?> thisClass;

  public ClassTest(String classPath, String className) {
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
   * @param className the expected class name
   * @param superclassType the expected superclass type
   * @param accessModifer the expected access modifier
   */
  public void testGeneralInfo(String className, Class<?> superclassType, int accessModifer) {
    assertEquals(className, thisClass.getName());
    assertEquals(superclassType, thisClass.getSuperclass());
    assertEquals(accessModifer, thisClass.getModifiers());

  }

  /**
   * A very shallow test of a constructor
   * 
   * @param constructor the constructor
   * @param accessModifer the expected access modifier
   */
  public void testConstructor(Constructor<?> constructor, int accessModifer) {
    assertEquals(accessModifer, constructor.getModifiers());
  }

  /**
   * A very shallow test of a method
   * 
   * @param method the method
   * @param accessModifier the expected access modifier
   * @param returnType the expected return type
   */
  public void testMethod(Method method, int accessModifer, Class<?> returnType) {
    assertEquals(accessModifer, method.getModifiers());
    assertEquals(returnType, method.getReturnType());
  }
}