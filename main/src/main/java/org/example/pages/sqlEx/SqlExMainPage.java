package org.example.pages.sqlEx;

import io.qameta.allure.Step;
import org.example.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SqlExMainPage extends BasePage {

    private static final String URL = "https://www.sql-ex.ru";

    @FindBy(css = "form[name='frmlogin'] input[name='login']")
    private WebElement loginField;

    @FindBy(css = "form[name='frmlogin'] input[name='psw']")
    private WebElement passwordField;

    @FindBy(css = "form[name='frmlogin'] input[name='subm1']")
    private WebElement loginSubmitButton;

    @FindBy(css = "b a[href='/personal.php']")
    private WebElement userNickname;

    @FindBy(css = "a[href='/logout.php']")
    private WebElement logoutButton;

    public SqlExMainPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие главной страницы \"sql-ex\"")
    public SqlExMainPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Ввод логина")
    public SqlExMainPage enterLogin(String login) {
        waitDisplayed(loginField);
        loginField.sendKeys(login);
        return this;
    }

    @Step("Ввод пароля")
    public SqlExMainPage enterPassword(String password) {
        waitDisplayed(passwordField);
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Нажатие кнопки \"Login\"")
    public SqlExMainPage submit() {
        waitDisplayed(loginSubmitButton);
        loginSubmitButton.click();
        return this;
    }

    @Step("Проверка на отображение никнейма")
    public boolean isNicknameDisplayed() {
        return waitDisplayed(userNickname);
    }

    @Step("Получение никнейма пользователя")
    public String getNickname() {
        waitDisplayed(userNickname);
        return userNickname.getText();
    }

    @Step("Проверка на отображение кнопки выхода из аккаунта")
    public boolean isLogoutButtonDisplayed() {
        return waitDisplayed(logoutButton);
    }

    @Step("Выход из аккаунта")
    public SqlExMainPage logout() {
        waitDisplayed(logoutButton);
        logoutButton.click();
        return this;
    }
}
