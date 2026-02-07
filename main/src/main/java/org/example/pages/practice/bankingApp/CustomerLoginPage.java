package org.example.pages.practice.bankingApp;

import io.qameta.allure.Step;
import org.example.pages.BasePage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Objects;

public class CustomerLoginPage extends BasePage {

    public static final String URL = "https://www.way2automation.com/angularjs-protractor/banking/#/customer";

    @FindBy(id = "userSelect")
    private WebElement yourNameComboBox;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(css = "div.box > div:nth-child(1) > strong")
    private WebElement customerWelcomeMessage;

    @FindBy(css = "button[ng-click='transactions()']")
    private WebElement headerTransactionsButton;

    @FindBy(css = "button[ng-click='deposit()']")
    private WebElement headerDepositButton;

    @FindBy(css = "button[ng-click='withdrawl()']")
    private WebElement headerWithdrawnButton;

    @FindBy(css = "input[ng-model='amount']")
    private WebElement amountField;

    @FindBy(css = "button[type='submit']")
    private WebElement depositButton;

    @FindBy(xpath = "//span[text()='Deposit Successful']")
    private WebElement depositSuccessfulMessage;

    @FindBy(xpath = "//span[text()='Transaction successful']")
    private WebElement withdrawnTransactionSuccessfulMessage;

    @FindBy(xpath = "//span[contains(text(),'Transaction Failed')]")
    private WebElement withdrawnTransactionsErrorMessage;

    @FindBy(xpath = "//td[text()='Credit']/parent::tr/td[2]")
    private List<WebElement> amountCreditList;

    @FindBy(xpath = "//td[text()='Debit']/parent::tr/td[2]")
    private List<WebElement> amountDebitList;

    @FindBy(css = "button[type='submit']")
    private WebElement withdrawButton;

    @FindBy(css = "button[ng-click='back()']")
    private WebElement transactionsBackButton;

    @FindBy(css = "button[ng-click='reset()']")
    private WebElement transactionsResetButton;

    @FindBy(css = "div[ng-hide='noAccount'] strong:nth-child(2)")
    private WebElement customerBalance;

    public CustomerLoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие страницы \"Customer Login\"")
    public CustomerLoginPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Выбор клиента из списка \"Your Name\"")
    public CustomerLoginPage selectCustomer(String customer) {
        waitDisplayed(yourNameComboBox);
        Select selectCustomer = new Select(yourNameComboBox);
        selectCustomer.selectByVisibleText(customer);
        return this;
    }

    @Step("Нажатие на кнопку \"Login\"")
    public String loginButtonClick() {
        waitDisplayed(loginButton);
        loginButton.click();

        waitDisplayed(customerWelcomeMessage);
        return customerWelcomeMessage.getText();
    }

    @Step("Ввод средств через \"Deposit\"")
    public String enterDeposit(Integer amount) {
        waitDisplayed(headerDepositButton);
        headerDepositButton.click();

        waitDisplayed(amountField);
        amountField.sendKeys(amount.toString());
        depositButton.click();

        try {
            waitDisplayed(depositSuccessfulMessage);
            return depositSuccessfulMessage.getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    /*
        Примечание: на сайте я не нашел кнопки "Withdrawn",
         допускается что имелось в виду "Withdrawl" в меню и кнопка подтверждения "Withdraw"
     */
    @Step("Вывод средств через \"Withdrawn\"")
    public String takeWithdrawn(Integer amount) {
        waitDisplayed(headerWithdrawnButton);
        headerWithdrawnButton.click();

        waitDisplayed(amountField);
        amountField.sendKeys(amount.toString());
        withdrawButton.click();

        try {
            waitDisplayed(withdrawnTransactionSuccessfulMessage);
            return withdrawnTransactionSuccessfulMessage.getText();
        } catch (TimeoutException e) {
            waitDisplayed(withdrawnTransactionsErrorMessage);
            return withdrawnTransactionsErrorMessage.getText();
        }
    }

    // без Thread.sleep - 5.3.1 и 5.3.3 будут flaky-тестами
    @Step("Переход на вкладку \"Transactions\"")
    public CustomerLoginPage moveToTransactions() {
        try {
            Thread.sleep(1000);
            waitDisplayed(headerTransactionsButton);
            headerTransactionsButton.click();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Step("Переход из вкладки \"Transactions\" в главное меню аккаунта клиента")
    public CustomerLoginPage backFromTransactions() {
        waitDisplayed(transactionsBackButton);
        transactionsBackButton.click();
        return this;
    }

    @Step("Проверка на существование записи о внесении средств в таблице \"Transactions\"")
    public boolean isAmountInCreditList(Integer amount) {
        moveToTransactions();
        boolean b = amountCreditList.stream().anyMatch(webElement ->
                Objects.equals(webElement.getText(), amount.toString()));
        backFromTransactions();
        return b;
    }

    @Step("Проверка на существование записи о снятии средств в таблице \"Transactions\"")
    public boolean isAmountInDebitList(Integer amount) {
        moveToTransactions();
        boolean b = amountDebitList.stream().anyMatch(webElement ->
                Objects.equals(webElement.getText(), amount.toString()));
        backFromTransactions();
        return b;
    }

    @Step("Получение баланса клиента")
    public Integer getCustomerBalance() {
        waitDisplayed(customerBalance);
        return Integer.parseInt(customerBalance.getText());
    }

    @Step("Получение баланса клиента из таблицы \"Transactions\"")
    public Integer getCustomerBalanceFromTransactions() {
        moveToTransactions();
        Integer creditSum = amountCreditList.stream()
                .mapToInt(webElement -> Integer.parseInt(webElement.getText())).sum();
        Integer debitSum = amountDebitList.stream()
                .mapToInt(webElement -> Integer.parseInt(webElement.getText())).sum();
        Integer balance = creditSum - debitSum;
        backFromTransactions();

        return balance;
    }

    @Step("Получение количества транзакций")
    public Integer getCountTransactions() {
        return amountCreditList.size() + amountDebitList.size();
    }

    @Step("Удаление всех транзакций и обнуление счета")
    public void resetTransactions() {
        waitDisplayed(transactionsResetButton);
        transactionsResetButton.click();
    }
}
