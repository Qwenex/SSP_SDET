import base.BaseTest;
import io.qameta.allure.*;
import org.example.pages.wiki.WikiMainPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Сайт ")
@Feature("Главная страница")
@Story("Проверка функционала")
public class WikiManPageTest extends BaseTest {

    public WikiMainPage wikiMainPage;

    @BeforeMethod
    public void newPage() {
        wikiMainPage = new WikiMainPage(webDriver);
        wikiMainPage.openPage();
    }

    @Severity(SeverityLevel.MINOR)
    @Test(description = "Фокус поля поиска")
    public void focusSearchFieldTest() {
        Assert.assertFalse(wikiMainPage.isSearchFieldInFocus(),
                "Поле поиска должно быть не в фокусе");

        wikiMainPage.clickOnSearchField();
        Assert.assertTrue(wikiMainPage.isSearchFieldInFocus(),
                "Поле поиска должно быть в фокусе");

        wikiMainPage.clearFocusFromSearchField();
        Assert.assertFalse(wikiMainPage.isSearchFieldInFocus(),
                "Поле поиска должно быть не в фокусе");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Скролл страницы")
    public void scrollPageTest() {
        Assert.assertFalse(wikiMainPage.checkScroll(),
                "Вертикальная прокрутка страницы должна отсутствовать");

        wikiMainPage.scrollDown(300);
        Assert.assertTrue(wikiMainPage.checkScroll(),
                "Вертикальная прокрутка страницы должна присутствовать");

        wikiMainPage.scrollUp(300);
        Assert.assertFalse(wikiMainPage.checkScroll(),
                "Вертикальная прокрутка страницы должна отсутствовать");
    }
}
