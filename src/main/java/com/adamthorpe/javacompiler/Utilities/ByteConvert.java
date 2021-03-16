package com.adamthorpe.javacompiler.Utilities;

import java.util.List;

import com.adamthorpe.javacompiler.ClassFile.ByteData;

public class ByteConvert {

  /**
   * <p>Converts a hex string into an array of bytes.</p>
   * 
   * @param hex         The input hex stream
   * @return            A byte array
   * @throws Exception  If <code>hex</code> contains an invalid character
   */
  public static byte[] hexToByteArray(String hex) throws Exception {

    // If the input hex has an odd number of characters, append a 0 to the front
    if(hex.length()%2 !=0) {
      hex="0"+hex;
    }

    byte[] out = new byte[ hex.length()/2 ];

    for (int i=0; i<(hex.length()/2); i++) {
      // Get the next two characters
      int d1 = getDigit(hex.charAt(i*2));
      int d2 = getDigit(hex.charAt(i*2 + 1));

      out[i] = (byte) ((d1 << 4) + d2);
    }

    return out;
  }

  /**
   * <p>Converts a hex character into an integer value.
   * Ranges from 0 to 127 and -128 to -1.</p>
   * 
   * @param letter      The hex character
   * @return            An integer representation of <code>letter</code>
   * @throws Exception  If the <code>letter</code> if not a valid hex character
   */
  protected static int getDigit(char letter) throws Exception {
    int digit = Character.digit(letter, 16);
    if (digit == -1) {
      throw new Exception();
    } else {
      return digit;
    }
  }

  /**
   * <p>Converts an integer into a byte representation, conforming to a byte <code>length</code>.
   * It does this by padding any extra bytes.</p>
   * 
   * @param length  The length of the new array
   * @param data    The data being converted into bytes
   * @return        A byte array
   */
  public static byte[] intToBytes(int length, int data) {
    byte[] output = new byte[length];

    for (int i=0; i<length; i++) {
      output[i] = (byte)((data >> (length-i-1)*8) & 0xff);
    }

    return output;
  }

  /**
   * <p>Converts one or many byte arrays into a single byte array.
   * It does this by copying each element into a new array.</p>
   * 
   * @param data  One or more byte arrays
   * @return      A single byte array
   */
  public static byte[] toByteArr(byte[] ...data) {

    // Calculate the length of the new array
    int length = 0;
    for(byte[] d: data) {
      length+=d.length;
    }

    // Create new array
    byte[] output = new byte[length];
    int counter = 0;

    // Copy elements into it
    for(byte[] d : data) {
      System.arraycopy(d, 0, output, counter, d.length);
      counter += d.length;
    }

    return output;
  }

  /**
   * <p>Converts an arbitary list of byte arrays into a single byte array.
   * It does this by copying each element into a new array.</p>
   * 
   * @param dataArray List of byte arrays
   * @return          A single byte array
   */
  public static byte[] toByteArr(List<? extends ByteData> dataArray) {
    
    // Calculate the length of the new array
    int length = 0;
    for (ByteData data : dataArray) {
      length+=data.getLength();
    }

    // Create new array
    byte[] output = new byte[length];
    int counter = 0;

    // Copy elements into it
    for (ByteData d : dataArray) {
      System.arraycopy(d.getData(), 0, output, counter, d.getLength());
      counter+=d.getLength();
    }

    return output;
  }

}
