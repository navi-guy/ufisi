package procesamientoOrdenes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesReader {
    private final static String FILE_NAME = "config.properties";

    public Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            System.out.println("Property file '" + FILE_NAME + "' not found in the classpath");
        }
        return properties;
    }
}