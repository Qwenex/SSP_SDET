package org.example.pages.way2automation;

import io.qameta.allure.Step;
import org.example.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends BasePage {

    private static final String URL = "https://www.way2automation.com/";

    @FindBy(css = "div.ast-above-header-wrap")
    private WebElement header;

    @FindBy(css = "#ast-desktop-header div.ast-primary-header")
    private WebElement navigationPanel;

    @FindBy(xpath = "//a[text()='Register Now']")
    private List<WebElement> registrationButtons;

    @FindBy(css = "div[data-id='259f3103'] > div")
    private WebElement coursesTable;

    @FindBy(css = "div[data-id='573bc308']")
    private WebElement footer;

    @FindBy(css = "div[data-id='df805ff'] li")
    private List<WebElement> headerContactInfoList;

    @FindBy(css = "div.header-social-inner-wrap")
    private WebElement socialNetworkLinksTable;

    @FindBy(css = "div[data-id='c50f9f0'] div.swiper-wrapper > div")
    private List<WebElement> mostPopularCoursesList;

    @FindBy(css = "div.swiper-button-prev-c50f9f0")
    private WebElement mostPopularCoursesPrevButton;

    @FindBy(css = "div.swiper-button-next-c50f9f0")
    private WebElement mostPopularCoursesNextButton;

    @FindBy(css = "div[data-id='695441a0'] li")
    private List<WebElement> footerAboutUsList;

    @FindBy(css = "li#menu-item-27580 > a > span.menu-text")
    private WebElement allCoursesMenu;

    @FindBy(css = "li#menu-item-27581 span.menu-text")
    private WebElement lifetimeMembershipClubButton;

    public MainPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие главной страницы \"Way2Automation\"")
    public MainPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Проверка на отображение Header")
    public boolean isHeaderDisplayed() {
        return waitDisplayed(header);
    }

    @Step("Проверка на отображение навигационной панели")
    public boolean isNavigationPanelDisplayed() {
        return waitDisplayed(navigationPanel);
    }

    @Step("Проверка на отображение кнопки регистрации")
    public boolean isRegistrationButtonDisplayed() {
        return wait.until(webDriver -> registrationButtons.stream().anyMatch(WebElement::isDisplayed));
    }

    @Step("Проверка на отображение таблицы с курсами")
    public boolean isCoursesTableDisplayed() {
        return waitDisplayed(coursesTable);
    }

    @Step("Проверка на отображение Footer")
    public boolean isFooterDisplayed() {
        return waitDisplayed(footer);
    }

    @Step("Проверка на отображение контактов в Header")
    public boolean isHeaderContactInfoDisplayed() {
        return wait.until(webDriver -> headerContactInfoList.stream().allMatch(WebElement::isDisplayed));
    }

    @Step("Проверка на отображение ссылок социальных сетей в Header")
    public boolean isSocialNetworkLinksTableDisplayed() {
        return waitDisplayed(socialNetworkLinksTable);
    }

    @Step("Проверка на отображение блока \"About Us\" в Footer")
    public boolean isFooterAboutUsItemsDisplayed() {
        return wait.until(webDriver -> footerAboutUsList.stream().allMatch(WebElement::isDisplayed));
    }

    @Step("Получение списка \"Most Popular Software Testing Courses\"")
    public List<WebElement> getMostPopularCoursesList() {
        wait.until(webDriver -> mostPopularCoursesList.stream().anyMatch(WebElement::isDisplayed));
        return mostPopularCoursesList;
    }

    @Step("Нажатие на кнопку \"Назад\" в списке \"Most Popular Software Testing Courses\"")
    public MainPage mostPopularCoursesPrevButtonClick() {
        mostPopularCoursesPrevButton.click();
        return this;
    }

    @Step("Нажатие на кнопку \"Вперед\" в списке \"Most Popular Software Testing Courses\"")
    public MainPage mostPopularCoursesNextButtonClick() {
        mostPopularCoursesNextButton.click();
        return this;
    }

    @Step("Переход на страницу \"Lifetime Membership Club\"")
    public LifetimeMembershipClubPage moveToLifetimeMembershipClubPage() {
        waitDisplayed(allCoursesMenu);
        allCoursesMenu.click();
        lifetimeMembershipClubButton.click();
        return new LifetimeMembershipClubPage(webDriver);
    }
}
