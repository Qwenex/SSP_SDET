package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class BaseTest {

    public WebDriver webDriver;
    public RemoteWebDriver remoteWebDriver;

    //@BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "----start-maximized",
                "--incognito"
        );
        webDriver = new ChromeDriver(options);
    }

    //@AfterMethod
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @BeforeMethod
    public void setUpGrid() throws MalformedURLException {
        String remoteURL = "http://localhost:4444/wd/hub";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        remoteWebDriver = new RemoteWebDriver(new URL(remoteURL), capabilities);
    }

    @AfterMethod
    public void tearDownGrid() {
        if (remoteWebDriver != null) {
            remoteWebDriver.quit();
        }
    }
}
