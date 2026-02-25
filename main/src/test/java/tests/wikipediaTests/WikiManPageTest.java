package tests.wikipediaTests;

import base.BaseTest;
import io.qameta.allure.*;
import org.example.pages.wiki.WikipediaMainPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Сайт Wikipedia")
@Feature("Главная страница")
@Story("Функциональность веб-элементов")
public class WikiManPageTest extends BaseTest {

    private WikipediaMainPage wikipediaMainPage;

    @BeforeMethod
    public void newPage() {
        wikipediaMainPage = new WikipediaMainPage(webDriver);
        wikipediaMainPage.openPage();
    }

    @Severity(SeverityLevel.MINOR)
    @Test(description = "Фокус поля поиска")
    public void focusSearchFieldTest() {
        Assert.assertFalse(wikipediaMainPage.isSearchFieldInFocus(),
                "Поле поиска должно быть не в фокусе");

        wikipediaMainPage.clickOnSearchField();
        Assert.assertTrue(wikipediaMainPage.isSearchFieldInFocus(),
                "Поле поиска должно быть в фокусе");

        wikipediaMainPage.clearFocusFromSearchField();
        Assert.assertFalse(wikipediaMainPage.isSearchFieldInFocus(),
                "Поле поиска должно быть не в фокусе");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Скролл страницы")
    public void scrollPageTest() {
        Assert.assertFalse(wikipediaMainPage.checkScroll(),
                "Вертикальная прокрутка страницы должна отсутствовать");

        wikipediaMainPage.scrollDown(300);
        Assert.assertTrue(wikipediaMainPage.checkScroll(),
                "Вертикальная прокрутка страницы должна присутствовать");

        wikipediaMainPage.scrollUp(300);
        Assert.assertFalse(wikipediaMainPage.checkScroll(),
                "Вертикальная прокрутка страницы должна отсутствовать");
    }
}
