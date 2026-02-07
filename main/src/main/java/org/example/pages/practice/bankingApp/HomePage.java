package org.example.pages.practice.bankingApp;

import io.qameta.allure.Step;
import org.example.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public static final String URL = "https://www.way2automation.com/angularjs-protractor/banking/#/login";

    @FindBy(css = "div.center:nth-child(1) a")
    private WebElement sampleFormButton;

    @FindBy(css = "div.center:nth-child(3) button")
    private WebElement customerLoginButton;

    @FindBy(css = "div.center:nth-child(5) button")
    private WebElement bankManagerLoginButton;

    @FindBy(css = "button.home")
    private WebElement homeButton;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("открытие главной страницы \"Way2Automation Banking App\"")
    public HomePage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Переход во вкладку \"Sample Form\"")
    public SampleFormPage moveToSampleFormPage() {
        waitDisplayed(sampleFormButton);
        sampleFormButton.click();
        return new SampleFormPage(webDriver);
    }

    @Step("Переход во вкладку \"Customer Login\"")
    public CustomerLoginPage moveToCustomerLoginPage() {
        waitDisplayed(customerLoginButton);
        customerLoginButton.click();
        return new CustomerLoginPage(webDriver);
    }

    @Step("Переход во вкладку \"Bank Manager Login\"")
    public BankManagerLoginPage moveToBankManagerLoginPage() {
        waitDisplayed(bankManagerLoginButton);
        bankManagerLoginButton.click();
        return new BankManagerLoginPage(webDriver);
    }

    @Step("Переход на главную страницу \"Way2Automation Banking App\"")
    public HomePage moveToHomePage() {
        waitDisplayed(homeButton);
        homeButton.click();
        return new HomePage(webDriver);
    }
}
