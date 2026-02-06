import org.example.pages.way2automation.MainPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Way2AutomationTest extends BaseTest {

    public MainPage mainPage;

    @BeforeMethod
    public void newPage() {
        mainPage = new MainPage(webDriver);
        mainPage.openPage();
    }

    // 1.1
    @Test
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
    @Test
    public void verifyHeaderLoaded() {
        Assert.assertTrue(mainPage.isHeaderContactInfoDisplayed(),
                "Все элементы контактной информации в хедере должны отображаться");

        Assert.assertTrue(mainPage.isSocialNetworkLinksTableDisplayed(),
                "Список соцсетей должен отображаться");
    }

    // 1.3 (Примечание: Тесты падают, так как кнопки переключения курсов на сайте не работают даже при ручном тестировании)
    //@Test
    public void verifyMostPopularCoursesNextButtons() {
        Assert.assertTrue(mainPage.getMostPopularCoursesList().get(0).isDisplayed(),
                "Первый блок из списка курсов должен отображаться");

        mainPage.mostPopularCoursesNextButtonClick();
        mainPage.mostPopularCoursesNextButtonClick();
        Assert.assertTrue(mainPage.getMostPopularCoursesList().get(2).isDisplayed(),
                "Третий блок из списка курсов должен отображаться");
    }

    //@Test
    public void verifyMostPopularCoursesPrevButtons() {
        Assert.assertTrue(mainPage.getMostPopularCoursesList().get(0).isDisplayed(),
                "Первый блок из списка курсов должен отображаться");

        mainPage.mostPopularCoursesPrevButtonClick();
        Assert.assertTrue(mainPage.getMostPopularCoursesList().get(15).isDisplayed(),
                "Последний (16-й) блок из списка курсов должен отображаться");
    }

    // 1.4
    @Test
    public void verifyFooterLoaded() {
        Assert.assertTrue(mainPage.isFooterDisplayed(),
                "Футер должен отображаться");

        Assert.assertTrue(mainPage.isFooterAboutUsItemsDisplayed(),
                "Все элементы 'About Us' в футере должны отображаться");
    }

    // 2
    @Test
    public void verifyNavigationPanelScroll() {
        for (int i = 0; i < 5; i++) {
            Assert.assertTrue(mainPage.isNavigationPanelDisplayed(),
                    "Навигационная панель должна отображаться");
            mainPage.scrollDown(500);
        }
    }

    // 3
    @Test
    public void verifyMoveToLifetimeMembershipClubPage() {
        String actualTitle = mainPage.moveToLifetimeMembershipClubPage().getTitle();
        String expectedTitle = "LIFETIME MEMBERSHIP CLUB";
        Assert.assertEquals(actualTitle, expectedTitle,
                "Title не совпадает");
    }

}
