package com.javarush.lesson07;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private Configuration() {

    }

    private static final Properties properties = new Properties();

    public static final String APPLICATION_PROPERTIES = "/application.properties";

    static {
        try {
            properties.load(Configuration.class.getResourceAsStream(
                    APPLICATION_PROPERTIES)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getString(String name) {
        //logic
        return System.getenv()
                .getOrDefault(name,
                        properties.getProperty(name)
                );
    }
}
