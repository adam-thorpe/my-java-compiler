import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyseClassFile {

  
  public static void main(String[] args) throws FileNotFoundException, IOException {

    File file = new File(args[0]);

    FileInputStream fis = new FileInputStream(file);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];
    try {
        for (int readNum; (readNum = fis.read(buf)) != -1;) {
            bos.write(buf, 0, readNum); //no doubt here is 0
            //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
            System.out.println("read " + readNum + " bytes,");
        }
    } catch (IOException ex) {
        Logger.getLogger("hello").log(Level.SEVERE, null, ex);
    }
    byte[] bytes = bos.toByteArray();

    System.out.print("Whole Program: ");
    for (byte b : bytes) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();

    byte[] magic_number = Arrays.copyOfRange(bytes, 0, 4); //u4
    byte[] minor_version = Arrays.copyOfRange(bytes, 4, 6); //u2
    byte[] major_version = Arrays.copyOfRange(bytes, 6, 8); // u2

    byte[] constant_pool_size = Arrays.copyOfRange(bytes, 8, 10); //u2
    int cp_size = hexToInt(constant_pool_size);
    byte[] constant_pool = Arrays.copyOfRange(bytes, 10, 10+cp_size); //cpsize

    System.out.print("Magic Number: ");
    for (byte b : magic_number) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();

    System.out.print("Minor Version: ");
    for (byte b : minor_version) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();

    System.out.print("Major Version: ");
    for (byte b : major_version) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();
    
    System.out.print("Constant Pool size: ");
    for (byte b : constant_pool_size) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println(" = " + cp_size);
    
    System.out.print("Constant Pool: ");
    for (byte b : constant_pool) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();

    byte[] access_flags = Arrays.copyOfRange(bytes, 10+cp_size, 12+cp_size); //u2
    byte[] this_class = Arrays.copyOfRange(bytes, 12+cp_size, 14+cp_size); //u2
    byte[] super_class = Arrays.copyOfRange(bytes, 14+cp_size, 16+cp_size); //u2

    System.out.print("Access Flags: ");
    for (byte b : access_flags) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();

    System.out.print("This Class: ");
    for (byte b : this_class) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();

    System.out.print("Super Class: ");
    for (byte b : super_class) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();

    byte[] interface_table_size = Arrays.copyOfRange(bytes, 16+cp_size, 18+cp_size); //u2
    int isize = hexToInt(interface_table_size);
    byte[] interface_table = Arrays.copyOfRange(bytes, 18+cp_size, 18+cp_size+isize); //isize

    System.out.print("Interface Table size: ");
    for (byte b : interface_table_size) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println(" = " + isize);

    System.out.print("Interface Table: ");
    for (byte b : interface_table) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();
    
    byte[] field_table_size = Arrays.copyOfRange(bytes, 18+cp_size+isize, 20+cp_size+isize); //u2
    int fsize = hexToInt(field_table_size);
    byte[] field_table = Arrays.copyOfRange(bytes, 20+cp_size+isize, 20+cp_size+isize+fsize); //fsize

    byte[] method_table_size = Arrays.copyOfRange(bytes, 20+cp_size+isize+fsize, 22+cp_size+isize+fsize); //u2
    int msize = hexToInt(method_table_size);
    byte[] method_table = Arrays.copyOfRange(bytes, 22+cp_size+isize+fsize, 22+cp_size+isize+fsize+msize); //msize

    byte[] attribute_table_size = Arrays.copyOfRange(bytes, 22+cp_size+isize+fsize+msize, 24+cp_size+isize+fsize+msize); //u2
    int asize = hexToInt(attribute_table_size);
    byte[] attribute_table = Arrays.copyOfRange(bytes, 24+cp_size+isize+fsize+msize, 24+cp_size+isize+fsize+msize+asize);; //asize

    System.out.println("Field Table size: " + fsize);

    System.out.print("Field Table: ");
    for (byte b : field_table) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();

    System.out.println("Method Table size: " + msize);

    System.out.print("Method Table: ");
    for (byte b : method_table) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();

    System.out.println("Attribute Table size: " + asize);

    System.out.print("Attribute Table: ");
    for (byte b : attribute_table) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }
    System.out.println();
  }

  protected static int hexToInt(byte[] bytes) {
    int len = (bytes.length-1)*2;
    int total=0;

    for (byte b : bytes) {
      total+=Byte.toUnsignedInt(b) * Math.pow(16, len);
      len-=2;
    }

    System.out.println(total);
    return total;
  }
}

