package com.adamthorpe.javacompiler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.adamthorpe.javacompiler.Compiler.Type;

import org.junit.Test;

public class TypeTest {

  @Test
  public void testFormat() {
    assertEquals("java/lang/String", Type.format("java.lang.String"));
    assertEquals("I", Type.format("I"));
  }

  @Test
  public void testGetName() {
    Type a = new Type("java/lang/String", false);
    Type b = new Type("I", true);
    Type c = new Type("java/lang/Object", false, 2);

    assertEquals("java/lang/String", a.getName());
    assertEquals("I", b.getName());
    assertEquals("java/lang/Object", c.getName());
  }
  
  @Test
  public void testGetFormalName() {
    Type a = new Type("java/lang/String", false);
    Type b = new Type("I", true);
    Type c = new Type("java/lang/Object", false, 2);

    assertEquals("Ljava/lang/String;", a.getFormalName());
    assertEquals("I", b.getFormalName());
    assertEquals("[[Ljava/lang/Object;", c.getFormalName());
  }
}
