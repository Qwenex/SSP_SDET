package utils;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    public enum Browser {
        CHROME, FIREFOX, EDGE
    }

    private static final Browser BROWSER = Browser.valueOf(
            System.getProperty("browser", "chrome").toUpperCase()
    );
    private static final String GRID_URL = System.getProperty("grid.url");

    public static WebDriver initDriver() {
        switch (BROWSER) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(
                        "--start-maximized",
                        "--incognito",
                        "--disable-notifications");
                return createDriver(chromeOptions);
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments(
                        "--start-maximized",
                        "--private");
                return createDriver(firefoxOptions);
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments(
                        "--start-maximized",
                        "--inprivate",
                        "--disable-notifications");
                return createDriver(edgeOptions);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + BROWSER);
        }
    }

    private static WebDriver createDriver(Object options) {
        if (GRID_URL != null && !GRID_URL.isEmpty()) {
            try {
                return new RemoteWebDriver(new URL(GRID_URL), (Capabilities) options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Grid URL: " + GRID_URL, e);
            }
        } else {
            if (options instanceof ChromeOptions) {
                return new ChromeDriver((ChromeOptions) options);
            } else if (options instanceof FirefoxOptions) {
                return new FirefoxDriver((FirefoxOptions) options);
            } else if (options instanceof EdgeOptions) {
                return new EdgeDriver((EdgeOptions) options);
            } else {
                throw new IllegalArgumentException("Unsupported options type: " + options.getClass());
            }
        }
    }
}
