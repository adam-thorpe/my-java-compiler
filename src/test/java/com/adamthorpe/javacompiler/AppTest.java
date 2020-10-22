package com.adamthorpe.javacompiler;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

/**
 * Test App
 */
public class TestApp {

  @Test
  public void noArgsShouldThrowException() {
    String[] args = {};

    Exception e = assertThrows(Exception.class, () -> App.parseArguments(args));
    assertTrue(e.getMessage().equals("No input file detected"));
  }
}