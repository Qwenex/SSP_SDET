package tests.way2automationTests.practice.jquery;

import base.BaseTest;
import io.qameta.allure.*;
import org.example.pages.way2automation.practice.way2autoJquery.FramesAndWindowsJqueryPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Сайт way2automation")
@Feature("Раздел Jquery")
public class FramesAndWindowsJqueryTest extends BaseTest {

    private FramesAndWindowsJqueryPage fawJP;

    @BeforeMethod
    public void newPage() {
        fawJP = new FramesAndWindowsJqueryPage(webDriver);
        fawJP.openPage();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Frames And Windows")
    @Description("Открытие новой вкладки, затем открытие новой вкладки из предыдущей")
    @Test(description = "Пункт Open New Window, переход по ссылке")
    public void openNewTabsTest() {
        Integer actualMessage = fawJP
                .openFrame(0)
                .clickNewBrowserTabLink()
                .clickNewBrowserTabLink()
                .getCountTabs();
        Integer expectedMessage = 3;

        Assert.assertEquals(actualMessage, expectedMessage,
                "Количество вкладок не совпадает с ожидаемым");
    }
}
