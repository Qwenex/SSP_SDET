package org.example.pages.practice.bankingApp;

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

    public HomePage openPage() {
        webDriver.get(URL);
        return this;
    }

    public SampleFormPage moveToSampleFormPage() {
        waitDisplayed(sampleFormButton);
        sampleFormButton.click();
        return new SampleFormPage(webDriver);
    }

    public CustomerLoginPage moveToCustomerLoginPage() {
        waitDisplayed(customerLoginButton);
        customerLoginButton.click();
        return new CustomerLoginPage(webDriver);
    }

    public BankManagerLoginPage moveToBankManagerLoginPage() {
        waitDisplayed(bankManagerLoginButton);
        bankManagerLoginButton.click();
        return new BankManagerLoginPage(webDriver);
    }

    public HomePage moveToHomePage() {
        waitDisplayed(homeButton);
        homeButton.click();
        return new HomePage(webDriver);
    }
}
