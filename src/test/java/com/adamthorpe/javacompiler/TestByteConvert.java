package com.adamthorpe.javacompiler;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestByteConvert {

  @Parameterized.Parameters
  public static Iterable<Object[]> data() {
      byte[] arr1 = {-85, -51};

      return Arrays.asList(new Object[][] { 
        {"ABCD", arr1 }
      });
  }

  private String input;
  private byte[] expected;

  public TestByteConvert(String input, byte[] expected) {
      this.input= input;
      this.expected= expected;
  }

  @Test
  public void testHexToByte() {
    assertArrayEquals(expected, ClassFile.hexToByteArray(input));
  }

  public void testHexToByteShouldFail() {

  }
}
