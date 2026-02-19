import base.BaseTest;
import io.qameta.allure.*;
import org.example.pages.practice.registration.AuthorizationPracticePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("Practice website")
@Feature("Registration")
public class AuthorizationPracticeTest extends BaseTest {

   public AuthorizationPracticePage authorizationPage;

    @BeforeMethod
    public void newPage() {
        authorizationPage = new AuthorizationPracticePage(webDriver);
        authorizationPage.openPage();
    }

    @DataProvider(name = "validUsers")
    public static Object[][] validUsersDataProvider() {
        return new Object[][]{
                {"angular", "password", ""},
                {"angular", "password", "I am angular!"}
        };
    }

    @DataProvider(name = "notValidUsers")
    public static Object[][] notValidUsersDataProvider() {
        return new Object[][]{
                {"Not_angular", "password", ""},
                {"Not_angular", "password", "Ok, iʼm not angular"}
        };
    }

    // 4.1
    @Severity(SeverityLevel.BLOCKER)
    @Story("Отображение элементов страницы")
    @Description("Проверка отображения полей \"Username\", \"Password\", \"Username Description\" и кнопки \"Login\"")
    @Test(description = "Проверка отображения полей ввода")
    public void verifyEnterFields() {
        Assert.assertTrue(authorizationPage.isUsernameFieldDisplayed(),
                "Поле \"Username\" должно отображаться");

        Assert.assertTrue(authorizationPage.isPasswordFieldDisplayed(),
                "Поле \"Password\" должно отображаться");

        Assert.assertTrue(authorizationPage.isUsernameDescriptionFieldDisplayed(),
                "Поле \"Username Description\" должно отображаться");

        Assert.assertFalse(authorizationPage.isActiveLoginButton(),
                "Кнопка \"Login\" в данный момент должна быть неактивна");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация пользователя")
    @Description("Валидная авторизация заполнив поля \"Username\", \"Password\" и \"Username Description\"")
    @Test(description = "Валидная авторизация заполнив 3 поля", dataProvider = "validUsers")
    public void validAuthTest(String username, String password, String usernameDescription) {
        String actualMassage = authorizationPage.auth(username, password, usernameDescription);
        String expectedMassage = "You're logged in!!";

        Assert.assertEquals(actualMassage, expectedMassage,
                "Сообщение об успешной авторизации отличается от ожидаемого");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация пользователя")
    @Description("Невалидная авторизация заполнив поля \"Username\", \"Password\" и \"Username Description\"")
    @Test(description = "Невалидная авторизация заполнив 3 поля", dataProvider = "notValidUsers")
    public void invalidAuthTest(String username, String password, String usernameDescription) {
        String actualMassage = authorizationPage.auth(username, password, usernameDescription);
        String expectedMassage = "Username or password is incorrect";

        Assert.assertEquals(actualMassage, expectedMassage,
                "Сообщение об неуспешной авторизации отличается от ожидаемого");
    }

    // 4.4
    @Severity(SeverityLevel.CRITICAL)
    @Story("Разлогирование аккаунта")
    @Description("Проверка успешного разлогирования с помощью кнопки \"Logout\"")
    @Test(description = "Успешное разлогирование", dataProvider = "validUsers")
    public void logoutTest(String username, String password, String usernameDescription) {
        String actualMassage = authorizationPage.auth(username, password, usernameDescription);
        String expectedMassage = "You're logged in!!";
        Assert.assertEquals(actualMassage, expectedMassage,
                "Сообщение об успешной авторизации отличается от ожидаемого");
        authorizationPage.logout();

        Assert.assertTrue(authorizationPage.isUsernameFieldDisplayed(),
                "Поле \"Username\" должно отображаться");

        Assert.assertTrue(authorizationPage.isPasswordFieldDisplayed(),
                "Поле \"Password\" должно отображаться");

        Assert.assertTrue(authorizationPage.isUsernameDescription2FieldDisplayed(),
                "Поле \"Username Description\" должно отображаться");
    }
}
