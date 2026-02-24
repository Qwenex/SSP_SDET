package org.example.pages.way2automation.practice.way2autoJquery;

import io.qameta.allure.Step;
import org.example.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class DroppableJqueryPage extends BasePage {

    private final String URL = way2automationURL + "/way2auto_jquery/droppable.php";

    @FindBy(id = "draggable")
    private WebElement draggableElement;

    @FindBy(id = "droppable")
    private WebElement droppableElement;

    public DroppableJqueryPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие страницы \"way2auto_jquery/droppable.php\"")
    public DroppableJqueryPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Перемещение draggable-элемента в зону droppable-элемента в Default functionality")
    public DroppableJqueryPage dropElement() {
        webDriver.switchTo().frame(0);

        waitDisplayed(draggableElement);
        waitDisplayed(droppableElement);
        actions = new Actions(webDriver);
        actions.dragAndDrop(draggableElement, droppableElement).perform();

        webDriver.switchTo().defaultContent();
        return this;
    }

    @Step("Получение сообщения из droppable - элемента")
    public String getMessageFromDroppableElement() {
        try {
            webDriver.switchTo().frame(0);
            return droppableElement.getText();
        } finally {
            webDriver.switchTo().defaultContent();
        }
    }
}
