package org.example.pages.practice.registration;

import io.qameta.allure.Step;
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

    @FindBy(id = "formly_2_input_username_0")
    private WebElement usernameDescription2Field;

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

    @Step("Открытие страницы \"Authorization Practice\"")
    public AuthorizationPracticePage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Проверка на отображение поля \"Username\"")
    public boolean isUsernameFieldDisplayed() {
        return waitDisplayed(usernameField);
    }

    @Step("Проверка на отображение поля \"Password\"")
    public boolean isPasswordFieldDisplayed() {
        return waitDisplayed(passwordField);
    }

    @Step("Проверка на отображение поля \"Username Description\"")
    public boolean isUsernameDescriptionFieldDisplayed() {
        return waitDisplayed(usernameDescriptionField);
    }

    @Step("Проверка на отображение поля \"Username Description2\"")
    public boolean isUsernameDescription2FieldDisplayed() {
        return waitDisplayed(usernameDescription2Field);
    }

    @Step("Проверка на отображение кнопки \"Login\"")
    public boolean isActiveLoginButton() {
        waitDisplayed(loginButton);
        return loginButton.isEnabled();
    }

    @Step("Авторизация с заполнением поля \"Username Description\" и получение сообщения об результате операции")
    public String auth(String username, String password, String usernameDescription) {
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
            try {
                waitDisplayed(errorLoggedInMassage);
                return errorLoggedInMassage.getText();
            } catch (Exception e2) {
               return "Сообщение не появилось";
            }
        }
    }

    @Step("Выход из аккаунта")
    public void logout() {
        waitDisplayed(logoutLink);
        logoutLink.click();
    }
}
