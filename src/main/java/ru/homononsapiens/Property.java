package ru.homononsapiens;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Property {
    private static Properties properties;

    public static synchronized String getProperty(String name) {
        if (properties == null) {
            properties = new Properties();
            try (InputStream in = Files.newInputStream(Paths.get("src/main/resources/application.properties"))) {
                properties.load(in);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return properties.getProperty(name);
    }
}
