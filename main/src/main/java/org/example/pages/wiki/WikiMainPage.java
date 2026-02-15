package org.example.pages.wiki;

import io.qameta.allure.Step;
import org.example.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WikiMainPage extends BasePage {

    private static final String URL = "https://ru.wikipedia.org/wiki/";

    @FindBy(id = "searchInput")
    private WebElement searchField;

    public WikiMainPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие главной страницы \"Wikipedia\"")
    public WikiMainPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Проверка фокуса поля поиска")
    public boolean isSearchFieldInFocus() {
        waitDisplayed(searchField);
        return checkFocus(searchField);
    }

    @Step("Фокус поля поиска")
    public WikiMainPage clickOnSearchField() {
        waitDisplayed(searchField);
        searchField.click();
        return this;
    }

    @Step("Очистка фокуса с поля поиска")
    public WikiMainPage clearFocusFromSearchField() {
        clearFocus(searchField);
        return this;
    }
}
