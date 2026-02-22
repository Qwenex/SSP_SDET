package tests.way2automationTests;

import base.BaseTest;
import io.qameta.allure.*;
import org.example.pages.way2automation.Way2AutomationMainPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Сайт way2automation")
@Feature("Главная страница")
public class Way2AutomationTest extends BaseTest {

    public Way2AutomationMainPage w2aMainPage;

    @BeforeMethod
    public void newPage() {
        w2aMainPage = new Way2AutomationMainPage(webDriver);
        w2aMainPage.openPage();
    }

    // 1.1
    @Severity(SeverityLevel.BLOCKER)
    @Story("Отображение элементов страницы")
    @Description("Проверка отображения основных элементов:" +
            " Header, Footer, кнопка регистрации, панель навигации и список курсов")
    @Test(description = "Открытие страницы")
    public void verifyMainPageLoaded() {
        Assert.assertTrue(w2aMainPage.isHeaderDisplayed(),
                "Header должен отображаться");

        Assert.assertTrue(w2aMainPage.isNavigationPanelDisplayed(),
                "Навигационная панель должна отображаться");

        Assert.assertTrue(w2aMainPage.isRegistrationButtonDisplayed(),
                "Должна быть хотя бы одна видимая кнопка регистрации");

        Assert.assertTrue(w2aMainPage.isCoursesTableDisplayed(),
                "Таблица курсов должна отображаться");

        Assert.assertTrue(w2aMainPage.isFooterDisplayed(),
                "Футер должен отображаться");
    }

    // 1.2
    @Severity(SeverityLevel.NORMAL)
    @Story("Отображение элементов страницы")
    @Description("Проверка хедера с контактной информацией:" +
            "номера телефонов, почта, ссылки на соц. сети")
    @Test(description = "Header с контактной информацией")
    public void verifyHeaderLoaded() {
        Assert.assertTrue(w2aMainPage.isHeaderContactInfoDisplayed(),
                "Все элементы контактной информации в хедере должны отображаться");

        Assert.assertTrue(w2aMainPage.isSocialNetworkLinksTableDisplayed(),
                "Список соцсетей должен отображаться");
    }

    // 1.3 (Примечание: Тесты падают, так как кнопки переключения курсов на сайте не работают даже при ручном тестировании)
    @Severity(SeverityLevel.NORMAL)
    @Story("Функциональность кнопок")
    @Description("Проверка кнопок переключения слайдов \"Most Popular Software Testing Courses\"")
    @Test(description = "Кнопка  переключения \"Вперед\" для слайдов с курсами")
    public void verifyMostPopularCoursesNextButtons() {
        Assert.assertTrue(w2aMainPage.getMostPopularCoursesList().get(0).isDisplayed(),
                "Первый блок из списка курсов должен отображаться");

        w2aMainPage.mostPopularCoursesNextButtonClick();
        w2aMainPage.mostPopularCoursesNextButtonClick();
        Assert.assertTrue(w2aMainPage.getMostPopularCoursesList().get(2).isDisplayed(),
                "Третий блок из списка курсов должен отображаться");
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Функциональность кнопок")
    @Description("Проверка кнопок переключения слайдов \"Most Popular Software Testing Courses\"")
    @Test(description = "Кнопка переключения \"Назад\" для слайдов с курсами")
    public void verifyMostPopularCoursesPrevButtons() {
        Assert.assertTrue(w2aMainPage.getMostPopularCoursesList().get(0).isDisplayed(),
                "Первый блок из списка курсов должен отображаться");

        w2aMainPage.mostPopularCoursesPrevButtonClick();
        Assert.assertTrue(w2aMainPage.getMostPopularCoursesList().get(15).isDisplayed(),
                "Последний (16-й) блок из списка курсов должен отображаться");
    }

    // 1.4
    @Severity(SeverityLevel.MINOR)
    @Story("Отображение элементов страницы")
    @Description("Проверка футера с контактной информацией:" +
            " адрес, номера телефонов и эмейлы")
    @Test(description = "Footer с контактной информацией")
    public void verifyFooterLoaded() {
        Assert.assertTrue(w2aMainPage.isFooterDisplayed(),
                "Футер должен отображаться");

        Assert.assertTrue(w2aMainPage.isFooterAboutUsItemsDisplayed(),
                "Все элементы 'About Us' в футере должны отображаться");
    }

    // 2
    @Severity(SeverityLevel.MINOR)
    @Story("Отображение элементов страницы")
    @Description("Проверка отображения меню навигации при скроллинге вниз")
    @Test(description = "Меню навигации при скроллинге")
    public void verifyNavigationPanelScroll() {
        for (int i = 0; i < 5; i++) {
            Assert.assertTrue(w2aMainPage.isNavigationPanelDisplayed(),
                    "Навигационная панель должна отображаться");
            w2aMainPage.scrollDown(500);
        }
    }

    // 3
    @Severity(SeverityLevel.NORMAL)
    @Story("Переход по ссылкам")
    @Description("Проверка перехода по меню навигации на страницу \"Lifetime membership\" и получение заголовка")
    @Test(description = "Переход на страницу \"Lifetime membership\"")
    public void verifyMoveToLifetimeMembershipClubPage() {
        String actualTitle = w2aMainPage.moveToLifetimeMembershipClubPage().getTitle();
        String expectedTitle = "LIFETIME MEMBERSHIP CLUB";
        Assert.assertEquals(actualTitle, expectedTitle,
                "Title не совпадает");
    }
}
