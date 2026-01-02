package utils;

import java.util.Objects;
import java.util.Properties;

public class PropertiesFileManager {
    private  final Properties props= new Properties();

    public PropertiesFileManager(Properties properties) {
        this.props.putAll(properties);
    }
    public PropertiesFileManager(String propertiesFile ) {
        try {

            props.load(Objects.requireNonNull(
                    getClass().getClassLoader().getResourceAsStream(propertiesFile),
                    "Properties file not found: " + propertiesFile));

        } catch (Exception e) {
            throw new RuntimeException("Error loading properties file: " + e.getMessage(), e);

        }

    }

    public String getProperty(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing property key: " + key);
        }
        return value.trim();    }


    public String getOrNull(String key) {
        String value = props.getProperty(key);
        return (value == null) ? null : value.trim();
    }



}
