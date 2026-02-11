package org.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public abstract class BasePage {

    public WebDriver webDriver;
    public WebDriverWait wait;
    public Actions actions;

    public Wait<WebDriver> fluentWait = new FluentWait<>(webDriver)
            .withTimeout(Duration.ofSeconds(10L))
            .pollingEvery(Duration.ofSeconds(1L))
            .ignoring(NoSuchElementException.class);

    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        this.actions = new Actions(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    @Step("Ожидание появления веб-элемента")
    public boolean waitDisplayed(WebElement webElement) {
        return wait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed();
    }

    @Step("Скролл до элемента")
    public BasePage scrollToElement(WebElement webElement) {
        actions.scrollToElement(webElement).perform();
        return this;
    }

    @Step("Скролл вниз")
    public BasePage scrollDown(Integer pixels) {
        actions.scrollByAmount(0, pixels).perform();
        return this;
    }

    @Step("Скролл вверх")
    public BasePage scrollUp(Integer pixels) {
        actions.scrollByAmount(0, -pixels).perform();
        return this;
    }

    @Step("Получение текста из Alert-сообщения")
    public String getTextFromAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = webDriver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
}
