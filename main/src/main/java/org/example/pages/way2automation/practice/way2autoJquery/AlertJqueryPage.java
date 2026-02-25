package org.example.pages.way2automation.practice.way2autoJquery;

import io.qameta.allure.Step;
import org.example.pages.base.BasePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AlertJqueryPage extends BasePage {

    private final String URL = way2automationURL + "/way2auto_jquery/alert.php";

    @FindBy(xpath = "//a[text()='Input Alert']")
    private WebElement inputAlertTabButton;

    @FindBy(css = "button[onclick='myFunction()']")
    private WebElement enterTextToAlertButton;

    @FindBy(id = "demo")
    private WebElement welcomeMessage;

    public AlertJqueryPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие страницы \"way2auto_jquery/alert\"")
    public AlertJqueryPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Перех на вкладку \"Input Alert\"")
    public AlertJqueryPage moveToInputAlertTab() {
        waitDisplayed(inputAlertTabButton);
        inputAlertTabButton.click();
        return this;
    }

    @Step("Ввод текста в JS-prompt alert")
    public AlertJqueryPage enterTextAlert(String text) {
        webDriver.switchTo().frame(1);
        waitDisplayed(enterTextToAlertButton);
        enterTextToAlertButton.click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = webDriver.switchTo().alert();
        alert.sendKeys(text);
        alert.accept();
        return this;
    }

    @Step("Получение приветственного сообщения")
    public String getTextFromWelcomeMessage() {
        waitDisplayed(welcomeMessage);
        return welcomeMessage.getText();
    }
}
