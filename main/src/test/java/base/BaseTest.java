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
    public void setUp(@Optional("chrome") String browser,
                      @Optional("") String gridUrl) {
        webDriver = DriverFactory.initDriver(browser, gridUrl);
    }

    @AfterMethod
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
