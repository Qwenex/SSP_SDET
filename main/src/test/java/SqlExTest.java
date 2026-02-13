import base.BaseTest;
import io.qameta.allure.*;
import org.example.pages.sqlEx.SqlExMainPage;
import org.example.utils.CookieFileManager;
import org.example.utils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Сайт sql-ex")
@Feature("Главная страница")
public class SqlExTest extends BaseTest {

    public SqlExMainPage sqlExMainPage;

    @BeforeMethod
    public void newPage() {
        sqlExMainPage = new SqlExMainPage(webDriver);
        sqlExMainPage.openPage();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация пользователя")
    @Test(description = "Авторизация пользователя с помощью cookies-файлов при их наличии")
    public void authTest() {
        String cookiesFilePath = "cookies";

        if (CookieFileManager.isCookiesExists(cookiesFilePath)) {
            CookieFileManager.loadCookies(webDriver, cookiesFilePath);
        } else {
            ReadProperty prop = new ReadProperty("sql/user1");
            boolean actualNicknameDisplayed = sqlExMainPage
                    .enterLogin(prop.get("login"))
                    .enterPassword(prop.get("password"))
                    .submit()
                    .isNicknameDisplayed();
            Assert.assertTrue(actualNicknameDisplayed,
                    "Никнейм не отображается - вход в аккаунт не был выполнен");

            String actualNickname = sqlExMainPage.getNickname();
            String expectedNickname = prop.get("nickname");
            Assert.assertEquals(actualNickname,expectedNickname,
                    "Никнейм пользователя не совпадает с ожидаемым: " + expectedNickname);

            CookieFileManager.saveCookies(webDriver, cookiesFilePath);
        }

        Assert.assertTrue(sqlExMainPage.isLogoutButtonDisplayed(),
                "Кнопка \"Logout\" не отображается - пользователь не авторизован");
    }
}
