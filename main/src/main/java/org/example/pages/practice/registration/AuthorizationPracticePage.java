package org.example.pages.practice.registration;

import org.example.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthorizationPracticePage extends BasePage {

    public static final String URL = "https://www.way2automation.com/angularjs-protractor/registeration/#/login";

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "formly_1_input_username_0")
    private WebElement usernameDescriptionField;

    @FindBy(css = "button[ng-click='Auth.login()']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[@class='ng-scope'][1]")
    private WebElement successfulLoggedInMassage;

    @FindBy(css = "div[ng-if='Auth.error']")
    private WebElement errorLoggedInMassage;

    @FindBy(css = "a[href='#/login']")
    private WebElement logoutLink;

    public AuthorizationPracticePage(WebDriver webDriver) {
        super(webDriver);
    }

    public AuthorizationPracticePage openPage() {
        webDriver.get(URL);
        return this;
    }

    public boolean isUsernameFieldDisplayed() {
        return waitDisplayed(usernameField);
    }

    public boolean isPasswordFieldDisplayed() {
        return waitDisplayed(passwordField);
    }

    public boolean isUsernameDescriptionFieldDisplayed() {
        return waitDisplayed(usernameDescriptionField);
    }

    public boolean isActiveLoginButton() {
        waitDisplayed(loginButton);
        return loginButton.isEnabled();
    }

    public String auth(String username, String password) {
        waitDisplayed(usernameField);
        usernameField.sendKeys(username);

        waitDisplayed(passwordField);
        passwordField.sendKeys(password);

        waitDisplayed(loginButton);
        loginButton.click();

        try {
            waitDisplayed(errorLoggedInMassage);
            return errorLoggedInMassage.getText();
        } catch (Exception e) {
            waitDisplayed(successfulLoggedInMassage);
            return successfulLoggedInMassage.getText();
        }
    }

    public String improvedAuth(String username, String password, String usernameDescription) {
        waitDisplayed(usernameField);
        usernameField.sendKeys(username);

        waitDisplayed(passwordField);
        passwordField.sendKeys(password);

        waitDisplayed(usernameDescriptionField);
        usernameDescriptionField.sendKeys(usernameDescription);

        waitDisplayed(loginButton);
        loginButton.click();

        try {
            waitDisplayed(successfulLoggedInMassage);
            return successfulLoggedInMassage.getText();
        } catch (Exception e) {
            waitDisplayed(errorLoggedInMassage);
            return errorLoggedInMassage.getText();
        }
    }

    public void logout() {
        waitDisplayed(logoutLink);
        logoutLink.click();
    }

}
