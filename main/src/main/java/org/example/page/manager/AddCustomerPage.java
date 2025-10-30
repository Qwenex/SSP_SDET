package org.example.page.manager;

import io.qameta.allure.Step;
import org.example.page.BasePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddCustomerPage extends BasePage {

    private final static String URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/addCust";

    @FindBy(css = "*[ng-model='fName']")
    private WebElement firstNameField;

    @FindBy(css = "*[ng-model='lName']")
    private WebElement lastNameField;

    @FindBy(css = "*[ng-model='postCd']")
    private WebElement postCodeField;

    @FindBy(css = "button[type='submit']")
    private WebElement addCustomerButton;

    public AddCustomerPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие страницы")
    public AddCustomerPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Ожидание видимости веб элемента")
    public void waitVisible(WebElement webElement) {
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    @Step("Ввод в поле \"First Name\"")
    public AddCustomerPage setFirstName(String firstName) {
        waitVisible(firstNameField);
        firstNameField.sendKeys(firstName);
        return this;
    }

    @Step("Ввод в поле \"Last Name\"")
    public AddCustomerPage setLastName(String lastName) {
        waitVisible(lastNameField);
        lastNameField.sendKeys(lastName);
        return this;
    }

    @Step("Ввод в поле \"Post Code\"")
    public AddCustomerPage setPostCode(String postCode) {
        waitVisible(postCodeField);
        postCodeField.sendKeys(postCode);
        return this;
    }

    @Step("Клик по кнопке: \"Добавить клиента\"")
    public AddCustomerPage clickSubmitButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addCustomerButton));
        addCustomerButton.click();
        return this;
    }

    @Step("Получение текста из Alert сообщения")
    public String getTextFromAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = webDriver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
}
