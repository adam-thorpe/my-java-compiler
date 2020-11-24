package com.adamthorpe.javacompiler;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestByteConvert {

  @Parameterized.Parameters
  public static Iterable<Object[]> data() {
      byte[] arr1 = {1};
      byte[] arr2 = {127};
      byte[] arr3 = {-128};
      byte[] arr4 = {-1};
      byte[] arr5 = {-54, -2, -70, -66};

      return Arrays.asList(new Object[][] { 
        {"01", arr1 },
        {"7F", arr2 },
        {"80", arr3 },
        {"FF", arr4 },
        {"CAFEBABE", arr5 },
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
    // TO DO
  }
}
