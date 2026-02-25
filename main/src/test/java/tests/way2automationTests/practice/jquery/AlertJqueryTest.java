package tests.way2automationTests.practice.jquery;

import base.BaseTest;
import io.qameta.allure.*;
import org.example.pages.way2automation.practice.way2autoJquery.AlertJqueryPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Сайт way2automation")
@Feature("Раздел Jquery")
public class AlertJqueryTest extends BaseTest {

    private AlertJqueryPage ajp;

    @BeforeMethod
    public void newPage() {
        ajp = new AlertJqueryPage(webDriver);
        ajp.openPage();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Alerts")
    @Description("Проверка применения пользовательского сообщения в JS-prompt alert")
    @Test(description = "Input Alert")
    public void enterCustomTextToAlertTest() {
        String actualMessage = ajp
                .moveToInputAlertTab()
                .enterTextAlert("Albert Einstein")
                .getTextFromWelcomeMessage();
        String expectedMessage = "Hello Albert Einstein! How are you today?";

        Assert.assertEquals(actualMessage, expectedMessage,
                "Приветственное сообщение отличается от ожидаемого");
    }
}
