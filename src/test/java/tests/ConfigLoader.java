package tests;
import java.io.FileInputStream;
import java.util.Properties;


public class ConfigLoader {
   private Properties properties;

    public ConfigLoader(String filePath) {
        loadProperties( filePath );
    }

  private void loadProperties(String filePath) {
      properties = new Properties();
      try {
          FileInputStream fileInputStream = new FileInputStream(filePath);
            properties.load(fileInputStream);
      } catch (Exception e) {
          System.out.println("Error loading properties file: " + e.getMessage());
      }
  }

  public  String getProperty(String key) {
      return properties.getProperty(key);
  }

}
