package org.example.pages.way2automation.practice.way2autoJquery;

import io.qameta.allure.Step;
import org.example.pages.base.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class DroppableJqueryPage extends BasePage {

    private static final String URL = "http://way2automation.com/way2auto_jquery/droppable.php";

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
    public String dropElementAndGetMessage() {
        try {
            webDriver.switchTo().frame(0);

            waitDisplayed(draggableElement);
            waitDisplayed(droppableElement);

            actions = new Actions(webDriver);
            actions.dragAndDrop(draggableElement, droppableElement).perform();
            return droppableElement.getText();
        } catch (TimeoutException | NoSuchElementException e) {
            return  null;
        } finally {
            webDriver.switchTo().defaultContent();
        }
    }
}
