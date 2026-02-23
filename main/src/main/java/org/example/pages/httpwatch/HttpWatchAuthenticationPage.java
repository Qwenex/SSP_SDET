package org.example.pages.httpwatch;

import io.qameta.allure.Step;
import org.example.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HttpWatchAuthenticationPage extends BasePage {

    private static final String URL = "https://www.httpwatch.com/httpgallery/authentication/";

    @FindBy(css = "input[value='Display Image']")
    private WebElement displayImageButton;

    @FindBy(id = "downloadImg")
    private WebElement authImg;

    public HttpWatchAuthenticationPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие страницы \"httpwatch - authentication\"")
    public HttpWatchAuthenticationPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Авторизация пользователя")
    public boolean authentication(String username, String password) {
        webDriver.get(String.format("https://%s:%s@www.httpwatch.com/httpgallery/authentication/",
                username, password));
        scrollToElement(displayImageButton);
        displayImageButton.click();
        return waitDisplayed(authImg);
    }
}
