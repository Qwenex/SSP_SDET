package org.example.pages.practice.bankingApp;

import io.qameta.allure.Step;
import org.example.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SampleFormPage extends BasePage {

    public static final String URL = "https://www.way2automation.com/angularjs-protractor/banking/registrationform.html";

    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "input[name='hobbies']")
    private List<WebElement> hobbiesCheckBox;

    @FindBy(id = "gender")
    private WebElement genderComboBox;

    @FindBy(id = "about")
    private WebElement aboutYourselfField;

    @FindBy(css = "button[type='submit']")
    private WebElement registerButton;

    @FindBy(id = "errorMessage")
    private WebElement registerErrorMessage;

    @FindBy(id = "successMessage")
    private WebElement registerSuccessMessage;

    @FindBy(css = "button.home")
    private WebElement homeButton;

    public SampleFormPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие страницы \"Sample Form\"")
    public SampleFormPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Ввод в поле\"First Name\"")
    public SampleFormPage setFirstName(String firstName) {
        firstNameField.sendKeys(firstName);
        return this;
    }

    @Step("Ввод в поле\"Last Name\"")
    public SampleFormPage setLastName(String lastName) {
        lastNameField.sendKeys(lastName);
        return this;
    }

    @Step("Ввод в поле\"Email\"")
    public SampleFormPage setEmail(String email) {
        emailField.sendKeys(email);
        return this;
    }

    @Step("Ввод в поле\"Password\"")
    public SampleFormPage setPassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Выбор хобби в чек-боксе \"Hobbies\"")
    public SampleFormPage setHobbies(String... hobbies) {
        for (String hobby : hobbies) {
            WebElement hobbyTarget = hobbiesCheckBox.stream().filter(webElement ->
                    Objects.equals(webElement.getAttribute("value"), hobby))
                    .findFirst().orElseThrow(NoSuchElementException::new);
            hobbyTarget.click();
        }
        return this;
    }

    @Step("Получение всего списка \"Hobbies\"")
    public List<String> getHobbiesList() {
        return hobbiesCheckBox.stream().map(webElement ->
                webElement.getAttribute("value")).toList();
    }

    @Step("Выбор гендера в меню \"Gender\"")
    public SampleFormPage setGender(String gender) {
        Select pickGender = new Select(genderComboBox);
        pickGender.selectByVisibleText(gender);
        return this;
    }

    @Step("Ввод сообщения в поле \"About Yourself\"")
    public SampleFormPage setAboutYourself(String aboutYourself) {
        aboutYourselfField.sendKeys(aboutYourself);
        return this;
    }

    @Step("Нажатие кнопки \"Register\" и получение сообщения об результате регистрации")
    public String submit() {
        scrollToElement(registerButton);
        registerButton.click();
        try {
            waitDisplayed(registerSuccessMessage);
            return registerSuccessMessage.getText();

        } catch (Exception e) {
            waitDisplayed(registerErrorMessage);
            return registerErrorMessage.getText();
        }
    }

    @Step("Переход на главную страницу \"Way2Automation Banking App\"")
    public HomePage moveToHomePage() {
        waitDisplayed(homeButton);
        homeButton.click();
        return new HomePage(webDriver);
    }
}
