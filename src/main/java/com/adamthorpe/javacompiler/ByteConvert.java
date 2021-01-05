package com.adamthorpe.javacompiler;

public class ByteConvert {

  public static byte[] hexToByteArray(String hex) {
    int len = hex.length();

    byte[] out = new byte[ len/2 ];

    for (int i=0; i<(len/2); i++) {
      int d1 = getDigit(hex.charAt(i*2));
      int d2 = getDigit(hex.charAt(i*2 + 1));

      out[i] = (byte) ((d1 << 4) + d2);
    }

    return out;
  }

  protected static int getDigit(char letter) {
    return Character.digit(letter, 16);
  }

  public static byte[] intToBytes(int length, int data) {
    byte[] output = new byte[length];

    for (int i=0; i<length; i++) {
      output[i] = (byte)((data >> (length-i-1)*8) & 0xff);
    }

    return output;
  }

  public static byte[] toByteArr(byte[] ...data) {
    int length=0;
    for(byte[] d: data) {
      length+=d.length;
    }

    byte[] output = new byte[length];
    int counter = 0;

    for(byte[] d : data) {
      System.arraycopy(d, 0, output, counter, d.length);
      counter += d.length;
    }

    return output;
  }
  
}
