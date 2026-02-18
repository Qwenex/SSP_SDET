package org.example.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {

    private final JavascriptExecutor js;

    public JavaScriptUtils(WebDriver webDriver) {
        this.js = (JavascriptExecutor) webDriver;
    }

    @Step("Очистка фокуса с элемента")
    public void blurElement(WebElement webElement) {
        js.executeScript("arguments[0].blur();", webElement);
    }

    @Step("Получение пикселей вертикальной прокрутки страницы")
    public Long getVerticalScroll() {
        return (Long) js.executeScript(
                "return window.pageYOffset;");
    }

    @Step("Получение пикселей горизонтальной прокрутки страницы")
    public Long getHorizontalScroll() {
        return (Long) js.executeScript(
                "return window.pageXOffset;");
    }
}
