package org.example.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    public WebDriver webDriver;
    public WebDriverWait wait;
    public Actions actions;

    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        this.actions = new Actions(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public boolean waitDisplayed(WebElement webElement) {
        return wait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed();
    }

    public BasePage scrollToElement(WebElement webElement) {
        actions.scrollToElement(webElement).perform();
        return this;
    }

    public BasePage scrollDown(Integer pixels) {
        actions.scrollByAmount(0, pixels).perform();
        return this;
    }

    public BasePage scrollUp(Integer pixels) {
        actions.scrollByAmount(0, -pixels).perform();
        return this;
    }

    public String getTextFromAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = webDriver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
}
