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

    public static WebDriver initDriver(String browserParam, String gridUrlParam) {
        Browser browser = Browser.valueOf(browserParam.trim().toUpperCase());
        boolean useGrid = gridUrlParam != null && !gridUrlParam.trim().isEmpty();
        String gridUrl = useGrid ? gridUrlParam.trim() : null;

        switch (browser) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(
                        "--start-maximized",
                        "--incognito",
                        "--disable-notifications");
                return createDriver(chromeOptions, gridUrl);
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments(
                        "--width=1920",
                        "--height=1080");
                return createDriver(firefoxOptions, gridUrl);
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments(
                        "--start-maximized",
                        "--inprivate",
                        "--disable-notifications");
                return createDriver(edgeOptions, gridUrl);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static WebDriver createDriver(Object options, String gridUrl) {
        if (gridUrl != null) {
            try {
                return new RemoteWebDriver(new URL(gridUrl), (Capabilities) options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Grid URL: " + gridUrl, e);
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
