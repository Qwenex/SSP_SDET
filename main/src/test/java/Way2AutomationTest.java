import base.BaseTest;
import io.qameta.allure.*;
import org.example.pages.way2automation.MainPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Сайт way2automation")
@Feature("Главная страница")
public class Way2AutomationTest extends BaseTest {

    public MainPage mainPage;

    @BeforeMethod
    public void newPage() {
        mainPage = new MainPage(webDriver);
        mainPage.openPage();
    }

    // 1.1
    @Severity(SeverityLevel.BLOCKER)
    @Story("Отображение элементов страницы")
    @Description("Проверка отображения основных элементов:" +
            " Header, Footer, кнопка регистрации, панель навигации и список курсов")
    @Test(description = "Открытие страницы")
    public void verifyMainPageLoaded() {
        Assert.assertTrue(mainPage.isHeaderDisplayed(),
                "Header должен отображаться");

        Assert.assertTrue(mainPage.isNavigationPanelDisplayed(),
                "Навигационная панель должна отображаться");

        Assert.assertTrue(mainPage.isRegistrationButtonDisplayed(),
                "Должна быть хотя бы одна видимая кнопка регистрации");

        Assert.assertTrue(mainPage.isCoursesTableDisplayed(),
                "Таблица курсов должна отображаться");

        Assert.assertTrue(mainPage.isFooterDisplayed(),
                "Футер должен отображаться");
    }

    // 1.2
    @Severity(SeverityLevel.NORMAL)
    @Story("Отображение элементов страницы")
    @Description("Проверка хедера с контактной информацией:" +
            "номера телефонов, почта, ссылки на соц. сети")
    @Test(description = "Header с контактной информацией")
    public void verifyHeaderLoaded() {
        Assert.assertTrue(mainPage.isHeaderContactInfoDisplayed(),
                "Все элементы контактной информации в хедере должны отображаться");

        Assert.assertTrue(mainPage.isSocialNetworkLinksTableDisplayed(),
                "Список соцсетей должен отображаться");
    }

    // 1.3 (Примечание: Тесты падают, так как кнопки переключения курсов на сайте не работают даже при ручном тестировании)
    @Severity(SeverityLevel.NORMAL)
    @Story("Функциональность кнопок")
    @Description("Проверка кнопок переключения слайдов \"Most Popular Software Testing Courses\"")
    @Test(description = "Кнопка  переключения \"Вперед\" для слайдов с курсами")
    public void verifyMostPopularCoursesNextButtons() {
        Assert.assertTrue(mainPage.getMostPopularCoursesList().get(0).isDisplayed(),
                "Первый блок из списка курсов должен отображаться");

        mainPage.mostPopularCoursesNextButtonClick();
        mainPage.mostPopularCoursesNextButtonClick();
        Assert.assertTrue(mainPage.getMostPopularCoursesList().get(2).isDisplayed(),
                "Третий блок из списка курсов должен отображаться");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Функциональность кнопок")
    @Description("Проверка кнопок переключения слайдов \"Most Popular Software Testing Courses\"")
    @Test(description = "Кнопка переключения \"Назад\" для слайдов с курсами")
    public void verifyMostPopularCoursesPrevButtons() {
        Assert.assertTrue(mainPage.getMostPopularCoursesList().get(0).isDisplayed(),
                "Первый блок из списка курсов должен отображаться");

        mainPage.mostPopularCoursesPrevButtonClick();
        Assert.assertTrue(mainPage.getMostPopularCoursesList().get(15).isDisplayed(),
                "Последний (16-й) блок из списка курсов должен отображаться");
    }

    // 1.4
    @Severity(SeverityLevel.MINOR)
    @Story("Отображение элементов страницы")
    @Description("Проверка футера с контактной информацией:" +
            " адрес, номера телефонов и эмейлы")
    @Test(description = "Footer с контактной информацией")
    public void verifyFooterLoaded() {
        Assert.assertTrue(mainPage.isFooterDisplayed(),
                "Футер должен отображаться");

        Assert.assertTrue(mainPage.isFooterAboutUsItemsDisplayed(),
                "Все элементы 'About Us' в футере должны отображаться");
    }

    // 2
    @Severity(SeverityLevel.MINOR)
    @Story("Отображение элементов страницы")
    @Description("Проверка отображения меню навигации при скроллинге вниз")
    @Test(description = "Меню навигации при скроллинге")
    public void verifyNavigationPanelScroll() {
        for (int i = 0; i < 5; i++) {
            Assert.assertTrue(mainPage.isNavigationPanelDisplayed(),
                    "Навигационная панель должна отображаться");
            mainPage.scrollDown(500);
        }
    }

    // 3
    @Severity(SeverityLevel.NORMAL)
    @Story("Переход по ссылкам")
    @Description("Проверка перехода по меню навигации на страницу \"Lifetime membership\" и получение заголовка")
    @Test(description = "Переход на страницу \"Lifetime membership\"")
    public void verifyMoveToLifetimeMembershipClubPage() {
        String actualTitle = mainPage.moveToLifetimeMembershipClubPage().getTitle();
        String expectedTitle = "LIFETIME MEMBERSHIP CLUB";
        Assert.assertEquals(actualTitle, expectedTitle,
                "Title не совпадает");
    }
}
