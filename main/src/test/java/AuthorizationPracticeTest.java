import io.qameta.allure.*;
import org.example.pages.practice.registration.AuthorizationPracticePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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

    // 4.1
    @Severity(SeverityLevel.BLOCKER)
    @Story("Отображение элементов страницы")
    @Description("Проверка отображения полей \"Username\" и \"Password\"")
    @Test(description = "Проверка отображения полей ввода")
    public void verifyEnterFields() {
        Assert.assertTrue(authorizationPage.isUsernameFieldDisplayed(),
                "Поле \"Username\" должно отображаться");

        Assert.assertTrue(authorizationPage.isPasswordFieldDisplayed(),
                "Поле \"Password\" должно отображаться");

        Assert.assertFalse(authorizationPage.isActiveLoginButton(),
                "Кнопка \"Login\" в данный момент должна быть неактивна");
    }

    // 4.2 (Примечание: Тест падает, так как на сайте кнопка Login неактивна при незаполненном поле usernameDescription)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация пользователя")
    @Description("Валидная авторизация заполнив поля \"Username\" и \"Password\"")
    @Test(description = "Валидная авторизация заполнив 2 поля")
    public void validAuthTest() {
        String username = "angular";
        String password = "password";

        String actualMassage = authorizationPage.auth(username, password);
        String expectedMassage = "You're logged in!!";

        Assert.assertEquals(actualMassage, expectedMassage,
                "Сообщение об успешной авторизации отличается от ожидаемого");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация пользователя")
    @Description("Валидная авторизация заполнив поля \"Username\", \"Password\" и \"Username Description\"")
    @Test(description = "Валидная авторизация заполнив 3 поля")
    public void validImprovedAuthTest() {
        String username = "angular";
        String password = "password";
        String usernameDescription = "I am angular!";

        String actualMassage = authorizationPage.improvedAuth(username, password, usernameDescription);
        String expectedMassage = "You're logged in!!";

        Assert.assertEquals(actualMassage, expectedMassage,
                "Сообщение об успешной авторизации отличается от ожидаемого");
    }

    //4.3 (Примечание: Тест падает, так как на сайте кнопка Login неактивна при незаполненном поле usernameDescription)
    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация пользователя")
    @Description("Невалидная авторизация заполнив поля \"Username\" и \"Password\"")
    @Test(description = "Невалидная авторизация заполнив 2 поля")
    public void invalidAuthTest() {
        String username = "Not_angular";
        String password = "password";

        String actualMassage = authorizationPage.auth(username, password);
        String expectedMassage = "Username or password is incorrect";

        Assert.assertEquals(actualMassage, expectedMassage,
                "Сообщение об неуспешной авторизации отличается от ожидаемого");
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация пользователя")
    @Description("Невалидная авторизация заполнив поля \"Username\", \"Password\" и \"Username Description\"")
    @Test(description = "Невалидная авторизация заполнив 3 поля")
    public void invalidImprovedAuthTest() {
        String username = "Not_angular";
        String password = "password";
        String usernameDescription = "Ok, iʼm not angular";

        String actualMassage = authorizationPage.improvedAuth(username, password, usernameDescription);
        String expectedMassage = "Username or password is incorrect";

        Assert.assertEquals(actualMassage, expectedMassage,
                "Сообщение об неуспешной авторизации отличается от ожидаемого");
    }

    // 4.4
    @Severity(SeverityLevel.CRITICAL)
    @Story("Разлогирование аккаунта")
    @Description("Проверка успешного разлогирования с помощью кнопки \"Logout\"")
    @Test(description = "Успешное разлогирование")
    public void logoutTest() {
        String username = "angular";
        String password = "password";
        String usernameDescription = "I am angular!";

        authorizationPage.improvedAuth(username, password, usernameDescription);
        authorizationPage.logout();

        Assert.assertTrue(authorizationPage.isUsernameFieldDisplayed(),
                "Поле \"Username\" должно отображаться");

        Assert.assertTrue(authorizationPage.isPasswordFieldDisplayed(),
                "Поле \"Password\" должно отображаться");

        Assert.assertFalse(authorizationPage.isUsernameDescriptionFieldDisplayed(),
                "Поле \"Username Description\" должно отображаться");
    }
}
