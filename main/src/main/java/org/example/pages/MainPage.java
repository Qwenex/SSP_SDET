package org.example.pages;

import io.qameta.allure.Step;
import org.example.utils.JavaScriptUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
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
import java.util.Objects;

public abstract class MainPage {

    public WebDriver webDriver;
    public WebDriverWait wait;
    public Actions actions;
    public JavaScriptUtils javaScriptUtils;

    public Wait<WebDriver> fluentWait = new FluentWait<>(webDriver)
            .withTimeout(Duration.ofSeconds(10L))
            .pollingEvery(Duration.ofSeconds(1L))
            .ignoring(NoSuchElementException.class);

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        this.actions = new Actions(webDriver);
        this.javaScriptUtils = new JavaScriptUtils(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    @Step("Ожидание появления веб-элемента")
    public boolean waitDisplayed(WebElement webElement) {
        try {
            return Objects.requireNonNull(wait.until(
                    ExpectedConditions.visibilityOf(webElement))).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Скролл до элемента")
    public MainPage scrollToElement(WebElement webElement) {
        actions.scrollToElement(webElement).perform();
        return this;
    }

    @Step("Скролл вниз")
    public MainPage scrollDown(Integer pixels) {
        actions.scrollByAmount(0, pixels).perform();
        return this;
    }

    @Step("Скролл вверх")
    public MainPage scrollUp(Integer pixels) {
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

    @Step("Проверка фокуса на элементе")
    public boolean checkFocus(WebElement webElement) {
        WebElement focusedElement = webDriver.switchTo().activeElement();
        return webElement.equals(focusedElement);
    }

    @Step("Проверка прокрутки страницы")
    public boolean checkScroll() {
        return javaScriptUtils.getVerticalScroll() != 0;
    }

    @Step("Очистка фокуса с элемента")
    public void clearFocus(WebElement webElement) {
        javaScriptUtils.blurElement(webElement);
    }

}
