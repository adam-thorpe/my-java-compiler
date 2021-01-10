package com.adamthorpe.javacompiler;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

public class TestByteConvert {
  @Test
  public void testHexToByte() {
    try {
      assertArrayEquals(new byte[] { 1 }, ByteConvert.hexToByteArray("1"));
      assertArrayEquals(new byte[] { 15 }, ByteConvert.hexToByteArray("F"));
      assertArrayEquals(new byte[] { 1 }, ByteConvert.hexToByteArray("01"));
      assertArrayEquals(new byte[] { 16 }, ByteConvert.hexToByteArray("10"));
      assertArrayEquals(new byte[] { 1, 1 }, ByteConvert.hexToByteArray("0101"));
      assertArrayEquals(new byte[] { 127 }, ByteConvert.hexToByteArray("7F"));
      assertArrayEquals(new byte[] { -128 }, ByteConvert.hexToByteArray("80"));
      assertArrayEquals(new byte[] { -1 }, ByteConvert.hexToByteArray("FF"));
      assertArrayEquals(new byte[] { -54, -2, -70, -66 }, ByteConvert.hexToByteArray("CAFEBABE"));
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void testIllegalCharacter() {
    assertThrows(Exception.class, () -> ByteConvert.getDigit('G'));
  }

  @Test
  public void testIntToBytes() {
    assertArrayEquals(new byte[]{}, ByteConvert.intToBytes(0, 1));
    assertArrayEquals(new byte[]{1}, ByteConvert.intToBytes(1, 1));
    assertArrayEquals(new byte[]{0, 1}, ByteConvert.intToBytes(2, 1));
    assertArrayEquals(new byte[]{0, 0, 0, 1}, ByteConvert.intToBytes(4, 1));
    assertArrayEquals(new byte[]{127}, ByteConvert.intToBytes(1, 127));
    assertArrayEquals(new byte[]{-128}, ByteConvert.intToBytes(1, 128));
    assertArrayEquals(new byte[]{-1}, ByteConvert.intToBytes(1, 255));
  }

  @Test
  public void testToByteArr() {
    assertArrayEquals(new byte[]{}, ByteConvert.toByteArr(new byte[][]{{}}));
    assertArrayEquals(new byte[]{1}, ByteConvert.toByteArr(new byte[][]{{1}}));
    assertArrayEquals(new byte[]{1, 2}, ByteConvert.toByteArr(new byte[][]{{1}, {2}}));
    assertArrayEquals(new byte[]{1, 2, 3, 4}, ByteConvert.toByteArr(new byte[][]{{1, 2}, {3, 4}}));
    assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6}, ByteConvert.toByteArr(new byte[][]{{1}, {2, 3}, {4, 5, 6}}));
  }
}
