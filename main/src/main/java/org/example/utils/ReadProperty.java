package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperty {

    private final Properties props;

    public ReadProperty(String path) {
        this.props = new Properties();
        String resourcePath = "credentials/" + path + ".properties";

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Файл не найден: " + resourcePath);
            }
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки: " + resourcePath, e);
        }
    }

    public String get(String key) {
        return props.getProperty(key);
    }
}