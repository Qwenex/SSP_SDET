package org.example.pages.way2automation.practice.way2autoJquery;

import io.qameta.allure.Step;
import org.example.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FramesAndWindowsJqueryPage extends BasePage {

    private final String URL = way2automationURL + "/way2auto_jquery/frames-and-windows.php#load_box";

    @FindBy(css = "div.farme_window a")
    private WebElement newBrowserTabLink;

    public FramesAndWindowsJqueryPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие страницы \"way2auto_jquery/frames-and-windows\"")
    public FramesAndWindowsJqueryPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Открытие фрейма")
    public FramesAndWindowsJqueryPage openFrame(Integer index) {
        webDriver.switchTo().frame(index);
        return this;
    }

    @Step("Закрытие фрейма")
    public FramesAndWindowsJqueryPage closeFrame(){
        webDriver.switchTo().defaultContent();
        return this;
    }

    @Step("Переход по ссылке \"New Browser Tab\" и открытие новой вкладки")
    public FramesAndWindowsJqueryPage clickNewBrowserTabLink() {
        waitDisplayed(newBrowserTabLink);
        newBrowserTabLink.click();
        for (String tab : webDriver.getWindowHandles()) {
            webDriver.switchTo().window(tab);
        }
        return this;
    }

    @Step("Получение количества вкладок")
    public Integer getCountTabs() {
        return webDriver.getWindowHandles().size();
    }
}
