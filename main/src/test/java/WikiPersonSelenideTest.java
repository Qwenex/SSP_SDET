import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.example.classes.MainPageSelenide;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

@Epic("Сайт Wikipedia")
@Feature("Проверка дат рождения и смерти известных личностей")
public class WikiPersonSelenideTest {

    MainPageSelenide mainPageSelenide = new MainPageSelenide();

    @BeforeMethod
    public void openPage() {
        mainPageSelenide.openPage();
    }

    @BeforeMethod
    private void initWebDriver() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();

        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("name", "Test badge...");
            put("sessionTimeout", "15m");
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});
            put("enableVNC", false);
            put("enableVideo", true);
        }});
        options.addArguments("--start-maximized");
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://selenoid:4444/wd/hub"), options);
        WebDriverRunner.setWebDriver(driver);
    }

    @AfterMethod
    private void closeWebDriver() {
        WebDriverRunner.getWebDriver().quit();
    }

    @DataProvider(name = "personNameAndBirthDate", parallel = true)
    public static Object[][] personBirthDataProvider() {
        return new Object[][]{
                {"Жак Фреско", "13 марта 1916"},
                {"Никола Тесла", "10 июля 1856"},
                {"Вин Дизель", "18 июля 1967"},
                {"Сергей Бодров мл.", "27 декабря 1971"}
        };
    }

    @DataProvider(name = "personNameAndDeathDate", parallel = true)
    public static Object[][] personDeathDataProvider() {
        return new Object[][]{
                {"Жак Фреско", "18 мая 2017"},
                {"Никола Тесла", "7 января 1943"},
                {"Вин Дизель", null},
                {"Сергей Бодров мл.", "20 сентября 2002"}
        };
    }

    @Story("Проверка даты рождения")
    @Test(description = "Соответствие даты рождения", dataProvider = "personNameAndBirthDate")
    public void personTest7(String name, String dateOfBirth) {
        String birthDateActual = mainPageSelenide
                .searchInputSendKeys(name)
                .searchInputSubmit()
                .getBirthDate();
        Assert.assertEquals(birthDateActual, dateOfBirth, "Дни рождения не совпадают: " + name);
    }

    @Story("Проверка даты смерти")
    @Test(description = "Соответствие даты смерти", dataProvider = "personNameAndDeathDate")
    public void personTest8(String name, String dateOfDeath) {
        try {
            String deathDateActual = mainPageSelenide
                    .searchInputSendKeys(name)
                    .searchInputSubmit()
                    .getDeathDate();
            Assert.assertEquals(deathDateActual, dateOfDeath, "Дни смерти не совпадают: " + name);
        } catch (ElementNotFound e) {
            System.out.printf("Для личности {%s} не найдено поле \"Дата смерти\"", name);
        }
    }
}
