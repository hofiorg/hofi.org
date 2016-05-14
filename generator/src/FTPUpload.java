import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FTPUpload {
  FTPClient client = new FTPClient();
  FileInputStream fis = null;


  public static void main(String[] args) {
    new FTPUpload().upload();
  }

  private void upload() {
    try {
      client.connect("ftp.hofi.org");
      client.login("yyyyyyy", "xxxxxxxx");

      String filename = "html/test.txt";
      fis = new FileInputStream(filename);

      client.storeFile(filename, fis);
      client.logout();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (fis != null) {
          fis.close();
        }
        client.disconnect();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
