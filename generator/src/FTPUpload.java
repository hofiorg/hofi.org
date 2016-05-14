import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class FTPUpload {

  private FTPClient client = new FTPClient();
  private FileInputStream fis = null;


  public static void main(String[] args) {
    new FTPUpload().upload();
  }

  private Properties loadProperties() throws IOException {
    Properties p = new Properties();
    p.load(getClass().getClassLoader().getResourceAsStream("ftp.properties"));
    return p;
  }

  private void upload() {
    try {
      Properties prop = loadProperties();

      String server = (String)prop.get("server");
      client.connect(server);

      System.out.println("Connected to " + server + ".");
      System.out.print(client.getReplyString());

      client.login((String)prop.get("login"), (String)prop.get("password"));

      removeDirectoryRecursive(client, "/html/article");

      System.out.println("");


      Files.walk(Paths.get("web" + File.separator + "article")).forEach(filePath -> {
        try {
          System.out.println("store: "+ filePath);
          String newName = filePath.toString().replace("web", "html");
          if (Files.isRegularFile(filePath)) {
            // file
            fis = new FileInputStream(filePath.toString());
            client.storeFile(newName, fis);

          } else {
            // directory
            client.makeDirectory(newName);
          }
        }
        catch(IOException ioe) {
          ioe.printStackTrace();
        }

      });

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

  private void removeDirectoryRecursive(FTPClient client, String dir) throws IOException{
    System.out.println("remove: " + dir);
    FTPFile[] list = client.listFiles(dir);
    for(FTPFile f : list) {
      if(f.getType() == 0) {
        client.deleteFile(dir + "/" + f.getName());
      }
      if(f.getType() == 1) {
        removeDirectoryRecursive(client, dir + "/" + f.getName());
      }
    }
    client.removeDirectory(dir);
  }
}
