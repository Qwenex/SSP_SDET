package org.example.pages.wiki;

import io.qameta.allure.Step;
import org.example.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WikipediaMainPage extends BasePage {

    private static final String URL = "https://ru.wikipedia.org/wiki/";

    @FindBy(id = "searchInput")
    private WebElement searchField;

    public WikipediaMainPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие главной страницы \"Wikipedia\"")
    public WikipediaMainPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Проверка фокуса поля поиска")
    public boolean isSearchFieldInFocus() {
        waitDisplayed(searchField);
        return checkFocus(searchField);
    }

    @Step("Фокус поля поиска")
    public WikipediaMainPage clickOnSearchField() {
        waitDisplayed(searchField);
        searchField.click();
        return this;
    }

    @Step("Очистка фокуса с поля поиска")
    public WikipediaMainPage clearFocusFromSearchField() {
        clearFocus(searchField);
        return this;
    }
}
