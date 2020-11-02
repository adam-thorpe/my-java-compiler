package com.adamthorpe.javacompiler;

public class ClassFile {
  private byte[] magic_number;
  private byte[] minor_version;   
  private byte[] major_version;

  public ClassFile() {
    this.magic_number = hexToByteArray("CAFEBABE");
    this.minor_version = hexToByteArray("");
    this.major_version = hexToByteArray("37");
  }

  protected static byte[] hexToByteArray(String hex) {
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
}
