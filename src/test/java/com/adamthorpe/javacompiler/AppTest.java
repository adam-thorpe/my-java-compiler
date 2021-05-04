package com.adamthorpe.javacompiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

/**
 * Test App.java
 */
public class AppTest {

  @Test
  public void noArgsShouldThrow() {
    String[] args = {};

    Exception e = assertThrows(Exception.class, () -> App.parseArguments(args));
    assertTrue(e.getMessage().equals("No input file detected."));
  }

  @Test
  public void tooManyArgsShouldThrow() {
    String[] args = {"1", "2"};

    Exception e = assertThrows(Exception.class, () -> App.parseArguments(args));
    assertTrue(e.getMessage().equals("Too many arguments supplied."));
  }

  @Test
  public void notAJavaFileShouldExit() {
    String[] notAJavaFile = {"bin/NotAJavaFile/NotAJavaFile.txt"};

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    assertDoesNotThrow(() -> App.main(notAJavaFile));
    assertEquals("Could not parse file: \"" + notAJavaFile[0] + "\"", outStream.toString().trim());
  }

  @Test
  public void invalidJavaFileShouldExit() {
    String[] invalidJavaFile = {"bin/InvalidJavaFile/InvalidJavaFile.java"};

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    assertDoesNotThrow(() -> App.main(invalidJavaFile));
    assertEquals("Could not parse file: \"" + invalidJavaFile[0] + "\"", outStream.toString().trim());
  }

  @Test
  public void fileDoesNotExistShouldExit() {
    String[] fileDoesNotExist = {"here/there/nowhere.java"};

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outStream));

    assertDoesNotThrow(() -> App.main(fileDoesNotExist));
    assertEquals("Could not find file: \"" + fileDoesNotExist[0] + "\"", outStream.toString().trim());
  }
}