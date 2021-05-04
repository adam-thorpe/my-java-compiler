package com.adamthorpe.javacompiler.Utilities;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.adamthorpe.javacompiler.ClassFile.ByteData;
import com.adamthorpe.javacompiler.ClassFile.DataTable;

import org.junit.Test;

/**
 * Test ByteConvert.java
 */
public class ByteConvertTest {
  @Test
  public void testHexToBytes() {
    try {
      assertArrayEquals(new byte[] { 1 }, ByteConvert.hexToBytes("1"));
      assertArrayEquals(new byte[] { 15 }, ByteConvert.hexToBytes("F"));
      assertArrayEquals(new byte[] { 1 }, ByteConvert.hexToBytes("01"));
      assertArrayEquals(new byte[] { 16 }, ByteConvert.hexToBytes("10"));
      assertArrayEquals(new byte[] { 1, 1 }, ByteConvert.hexToBytes("0101"));
      assertArrayEquals(new byte[] { 127 }, ByteConvert.hexToBytes("7F"));
      assertArrayEquals(new byte[] { -128 }, ByteConvert.hexToBytes("80"));
      assertArrayEquals(new byte[] { -1 }, ByteConvert.hexToBytes("FF"));
      assertArrayEquals(new byte[] { -54, -2, -70, -66 }, ByteConvert.hexToBytes("CAFEBABE"));
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
    assertArrayEquals(new byte[]{1, 0}, ByteConvert.intToBytes(2, 256));
  }

  @Test
  public void testCopyBytes() {
    assertArrayEquals(new byte[]{}, ByteConvert.copyBytes(new byte[][]{{}}));
    assertArrayEquals(new byte[]{1}, ByteConvert.copyBytes(new byte[][]{{1}}));
    assertArrayEquals(new byte[]{1, 2}, ByteConvert.copyBytes(new byte[][]{{1}, {2}}));
    assertArrayEquals(new byte[]{1, 2, 3, 4}, ByteConvert.copyBytes(new byte[][]{{1, 2}, {3, 4}}));
    assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6}, ByteConvert.copyBytes(new byte[][]{{1}, {2, 3}, {4, 5, 6}}));
  }

  @Test
  public void testCopyBytes2() {
    //Set up data classes
    class MyByteData implements ByteData {
      private byte[] data;

      public MyByteData(byte[] data) {
        this.data=data;
      }

      @Override
      public int getLength() {
        return data.length;
      }

      @Override
      public byte[] getData() {
        return data;
      }
    }

    class MyDataTable extends DataTable<MyByteData> {
      public MyDataTable(byte[] ...arr) {
        for (byte[] data : arr) {
          add(new MyByteData(data));
        }
      }
    }

    //Test
    MyDataTable testA = new MyDataTable(new byte[]{});
    MyDataTable testB = new MyDataTable(new byte[]{1,2,3});
    MyDataTable testC = new MyDataTable(new byte[]{1}, new byte[]{2,3}, new byte[]{4,5,6});

    assertArrayEquals(new byte[]{}, ByteConvert.copyBytes(testA));
    assertArrayEquals(new byte[]{1,2,3}, ByteConvert.copyBytes(testB));
    assertArrayEquals(new byte[]{1,2,3,4,5,6}, ByteConvert.copyBytes(testC));
  }
}