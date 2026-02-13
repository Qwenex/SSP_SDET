package org.example.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.Set;

public class CookieFileManager {

    public static void saveCookies(WebDriver webDriver, String filePath) {
        Set<Cookie> cookies = webDriver.manage().getCookies();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(cookies);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения cookies: " + filePath, e);
        }
    }

    public static void loadCookies(WebDriver webDriver, String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Set<Cookie> cookies = (Set<Cookie>) ois.readObject();
            webDriver.manage().deleteAllCookies();
            for (Cookie cookie : cookies) {
                webDriver.manage().addCookie(cookie);
            }
            webDriver.navigate().refresh();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Ошибка загрузки cookies: " + filePath, e);
        }
    }

    public static boolean isCookiesExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.length() > 0;
    }
}
