package tests.way2automationTests.practice.jquery;

import base.BaseTest;
import io.qameta.allure.*;
import org.example.pages.way2automation.practice.way2autoJquery.DroppableJqueryPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Сайт way2automation")
@Feature("Раздел Jquery")
public class DragAndDropJqueryTest extends BaseTest {

    public DroppableJqueryPage djp;

    @BeforeMethod
    public void newPage() {
        djp = new DroppableJqueryPage(webDriver);
        djp.openPage();
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Default functionality")
    @Test(description = "Перемещение draggable-элемента в зону droppable-элемента")
    public void droppingTest() {
        String expectedMassage = "Dropped!";
        String actualMassage = djp.dropElementAndGetMessage();
        Assert.assertEquals(actualMassage, expectedMassage,
                "Сообщение в \"Droppable element\" после перемещения в него другого элемента " +
                        "отличается от ожидаемого");
    }
}
