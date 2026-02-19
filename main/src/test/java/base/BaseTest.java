package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.DriverFactory;

public abstract class BaseTest {

    public WebDriver webDriver;

    @BeforeMethod
    @Parameters({"browser", "grid.url"})
    public void setUp(@Optional("") String browser,
                      @Optional("") String gridUrl) {
        if (browser != null && !browser.isEmpty() && System.getProperty("browser") == null) {
            System.setProperty("browser", browser);
        }
        if (gridUrl != null && !gridUrl.isEmpty() && System.getProperty("grid.url") == null) {
            System.setProperty("grid.url", gridUrl);
        }

        webDriver = DriverFactory.initDriver();
    }

    @AfterMethod
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
