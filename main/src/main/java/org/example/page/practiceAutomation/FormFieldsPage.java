package org.example.page.practiceAutomation;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class FormFieldsPage {

    private final static String URL = "https://practice-automation.com/form-fields/";

    @FindBy(id = "name-input")
    private WebElement nameField;

    @FindBy(css = "*[type='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@name='fav_drink']")
    private List<WebElement> favoriteDrinkCheckBox;

    @FindBy(xpath = "//*[@name='fav_color']")
    private List<WebElement> favoriteColorRadioBox;

    @FindBy(id = "automation")
    private WebElement likeAutomationComboBox;

    @FindBy(css = "#feedbackForm>ul>li")
    private List<WebElement> automationToolsList;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "message")
    private WebElement massageTextArea;

    @FindBy(id = "submit-btn")
    private WebElement submitButton;

    private final WebDriver webDriver;
    private final WebDriverWait wait;

    public FormFieldsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        PageFactory.initElements(webDriver, this);
    }

    @Step("Открытие страницы")
    public FormFieldsPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Закрытие страницы")
    public void closePage() {
        webDriver.quit();
    }

    @Step("Скролл страницы до элемента")
    private void scrollToElement(WebElement webElement) {
        Actions actions = new Actions(webDriver);
        actions.moveToElement(webElement);
        actions.perform();
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    @Step("Ввод в поле \"Name\"")
    public FormFieldsPage setName(String name) {
        nameField.sendKeys(name);
        return this;
    }

    @Step("Ввод в поле \"Password\"")
    public FormFieldsPage setPassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Выбор \"What is your favorite drink?\" в чек-боксе")
    public FormFieldsPage pickFavoriteDrink(String... drinks) {
        for (String drink : drinks) {
            WebElement drinkTarget = favoriteDrinkCheckBox.stream().filter(webElement ->
                            Objects.equals(webElement.getAttribute("value"), drink))
                    .findFirst().orElseThrow(NoSuchElementException::new);
            scrollToElement(drinkTarget);
            drinkTarget.click();
        }
        return this;
    }

    @Step("Выбор \"What is your favorite color?\" в радио-боксе")
    public FormFieldsPage pickFavoriteColor(String color) {
        WebElement colorTarget = favoriteColorRadioBox.stream().filter(webElement ->
                        Objects.equals(webElement.getAttribute("value"), color))
                .findFirst().orElseThrow(NoSuchElementException::new);
        scrollToElement(colorTarget);
        colorTarget.click();
        return this;
    }

    @Step("Выбор ответа \"Do you like automation?\" из выпадающего списка")
    public FormFieldsPage pickDoYouLikeAutomation(String answer) {
        Select likeAutomation = new Select(likeAutomationComboBox);
        likeAutomation.selectByVisibleText(answer);
        return this;
    }

    @Step("Ввод значения в поле \"Email\"")
    public FormFieldsPage setEmail(String email) {
        emailField.sendKeys(email);
        return this;
    }

    @Step("Получение количества инструментов из списка \"Automation tools\"")
    public Integer getNumberOfTools() {
        return automationToolsList.size();
    }

    @Step("Получение списка инструмнтов из \"Automation tools\"")
    public List<String> getToolsList() {
        return automationToolsList.stream().map(WebElement::getText).toList();
    }

    @Step("Ввод в поле \"Message\"")
    public FormFieldsPage setMassageText(String massage) {
        massageTextArea.sendKeys(massage);
        return this;
    }

    @Step("Клик по кнопке \"Submit\"")
    public FormFieldsPage clickSubmitButton() {
        scrollToElement(submitButton);
        submitButton.click();
        return this;
    }

    @Step("Получение сообщения из Alert сообщения")
    public String getTextFromAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = webDriver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
}