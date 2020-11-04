import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    for (byte b : bytes) {
      String st = String.format("%02X", b);
      System.out.print(st);
    }

    System.out.println("");

  }
}

