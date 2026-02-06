package org.example.pages.way2automation;

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

    public MainPage openPage() {
        webDriver.get(URL);
        return this;
    }

    public boolean isHeaderDisplayed() {
        return waitDisplayed(header);
    }

    public boolean isNavigationPanelDisplayed() {
        return waitDisplayed(navigationPanel);
    }

    public boolean isRegistrationButtonDisplayed() {
        return wait.until(webDriver -> registrationButtons.stream().anyMatch(WebElement::isDisplayed));
    }

    public boolean isCoursesTableDisplayed() {
        return waitDisplayed(coursesTable);
    }

    public boolean isFooterDisplayed() {
        return waitDisplayed(footer);
    }

    public boolean isHeaderContactInfoDisplayed() {
        return wait.until(webDriver -> headerContactInfoList.stream().allMatch(WebElement::isDisplayed));
    }

    public boolean isSocialNetworkLinksTableDisplayed() {
        return waitDisplayed(socialNetworkLinksTable);
    }

    public boolean isFooterAboutUsItemsDisplayed() {
        return wait.until(webDriver -> footerAboutUsList.stream().allMatch(WebElement::isDisplayed));
    }

    public List<WebElement> getMostPopularCoursesList() {
        wait.until(webDriver -> mostPopularCoursesList.stream().anyMatch(WebElement::isDisplayed));
        return mostPopularCoursesList;
    }

    public MainPage mostPopularCoursesPrevButtonClick() {
        mostPopularCoursesPrevButton.click();
        return this;
    }

    public MainPage mostPopularCoursesNextButtonClick() {
        mostPopularCoursesNextButton.click();
        return this;
    }

    public LifetimeMembershipClubPage moveToLifetimeMembershipClubPage() {
        waitDisplayed(allCoursesMenu);
        allCoursesMenu.click();
        lifetimeMembershipClubButton.click();
        return new LifetimeMembershipClubPage(webDriver);
    }

}
