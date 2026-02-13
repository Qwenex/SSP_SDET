package org.example.pages.practice.bankingApp;

import io.qameta.allure.Step;
import org.example.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.NoSuchElementException;

public class BankManagerLoginPage extends BasePage {

    public static final String URL = "https://www.way2automation.com/angularjs-protractor/banking/#/manager";

    @FindBy(css = "button[ng-click='addCust()']")
    private WebElement headerAddCustomerButton;

    @FindBy(css = "button[ng-click='openAccount()']")
    private WebElement headerOpenAccountButton;

    @FindBy(css = "button[ng-click='showCust()']")
    private WebElement headerCustomersButton;

    @FindBy(css = "input[ng-model='fName']")
    private WebElement firstNameField;

    @FindBy(css = "input[ng-model='lName']")
    private WebElement lastNameField;

    @FindBy(css = "input[ng-model='postCd']")
    private WebElement postCodeField;

    @FindBy(css = "button[type='submit']")
    private WebElement addCustomerButton;

    @FindBy(id = "userSelect")
    private WebElement customerComboBox;

    @FindBy(id = "currency")
    private WebElement currencyComboBox;

    @FindBy(css = "button[type='submit']")
    private WebElement processButton;

    @FindBy(css = "input[ng-model='searchCustomer']")
    private WebElement searchCustomersField;

    @FindBy(css = "tbody td:nth-child(1)")
    private List<WebElement> firstNamesTableCells;

    @FindBy(css = "button.home")
    private WebElement homeButton;

    public static final String dynamicDeleteCustomerButton =
            "//td[text()='%s']/..//button[text()='Delete']";

    public BankManagerLoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие страницы \"Bank Manager Login\"")
    public BankManagerLoginPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Добавление клиента и получение текста из алерта")
    public String addCustomerAndGetAlert(String firstName, String lastName, String postCode) {
        waitDisplayed(headerAddCustomerButton);
        headerAddCustomerButton.click();

        waitDisplayed(firstNameField);
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        postCodeField.sendKeys(postCode);
        addCustomerButton.click();

        return getTextFromAlert();
    }

    @Step("Открытие аккаунта клиента и получение текста из алерта")
    public String openAccountAndGetAlert(String customer, String currency) {
        waitDisplayed(headerOpenAccountButton);
        headerOpenAccountButton.click();

        waitDisplayed(customerComboBox);
        Select selectCustomer = new Select(customerComboBox);
        selectCustomer.selectByVisibleText(customer);

        Select selectCurrency = new Select(currencyComboBox);
        selectCurrency.selectByValue(currency);
        processButton.click();

        return getTextFromAlert();
    }

    @Step("Переход на вкладку \"Customers\"")
    public BankManagerLoginPage moveToCustomersList() {
        waitDisplayed(headerCustomersButton);
        headerCustomersButton.click();
        return this;
    }

    @Step("Проверка существования клиента в таблице \"Customers\"")
    public boolean isCustomerExist(String customer) {
        waitDisplayed(searchCustomersField);
        searchCustomersField.sendKeys(customer);
        return firstNamesTableCells.stream().anyMatch(webElement ->
                webElement.getText().equals(customer));
    }

    @Step("Очистка поля поиска в таблице \"Customers\"")
    public void clearSearch() {
        searchCustomersField.clear();
    }

    /**
     * Удаление клиента по имени, фамилии или post code
     * @param value Имя или фамилии или post code клиента
     */
    @Step("Удаление клиента из таблицы \"Customers\"")
    public BankManagerLoginPage deleteCustomer(String value) {
        String xpath = String.format(dynamicDeleteCustomerButton, value);
        try {
            WebElement deleteButton = webDriver.findElement(By.xpath(xpath));
            scrollToElement(deleteButton);
            deleteButton.click();
        } catch (NoSuchElementException e) {
            System.out.printf("Кнопка удаления у \"%s\" в списке не найдена. %s", value, e);
        }
        return this;
    }

    @Step("Переход на главную страницу \"Way2Automation Banking App\"")
    public HomePage moveToHomePage() {
        waitDisplayed(homeButton);
        homeButton.click();
        return new HomePage(webDriver);
    }
}
