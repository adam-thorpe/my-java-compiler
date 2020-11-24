package com.adamthorpe.javacompiler;

public class ByteConvert {

  // public static byte[] stringToBytes(String input) {
  //   byte[] output = new byte[input.length()];

  //   for(int i=0; i<0; i++) {
  //     // output[i] = Byte.parseByte(String.valueOf(input.charAt(i)));
  //     output[i] = (byte) input.charAt(i);
  //   }

  //   return output;
  // }

  public static byte[] intToBytes(int length, int data) {
    byte[] output = new byte[length];

    for (int i=0; i<length; i++) {
      output[i] = (byte)((data >> (length-i-1)*8) & 0xff);
    }

    return output;
  }
  
}
