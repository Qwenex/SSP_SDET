package tests.httpwatchTests;

import base.BaseTest;
import io.qameta.allure.*;
import org.example.pages.httpwatch.HttpWatchAuthenticationPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Сайт httpwatch")
@Feature("authentication")
public class HttpWatchAuthenticationTest extends BaseTest {

    private HttpWatchAuthenticationPage hwAuthPage;

    @BeforeMethod
    public void newPage() {
        hwAuthPage = new HttpWatchAuthenticationPage(webDriver);
        hwAuthPage.openPage();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация пользователя")
    @Test(description = "Авторизация пользователя через всплывающее окно браузера")
    public void authTest() {
        Assert.assertTrue(hwAuthPage.authentication("httpwatch","httpwatch"),
                "Ожидалось появление изображения \"HTTP Basic Authentication\"");
    }
}
