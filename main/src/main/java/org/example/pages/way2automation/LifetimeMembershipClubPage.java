package org.example.pages.way2automation;

import io.qameta.allure.Step;
import org.example.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LifetimeMembershipClubPage extends BasePage {

    private static final String URL = "https://www.way2automation.com/lifetime-membership-club/";

    @FindBy(css = "html h1")
    private WebElement title;

    public LifetimeMembershipClubPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие страницы \"Lifetime Membership Club\"")
    public LifetimeMembershipClubPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Получение заголовка страницы")
    public String getTitle() {
        return title.getText();
    }
}
