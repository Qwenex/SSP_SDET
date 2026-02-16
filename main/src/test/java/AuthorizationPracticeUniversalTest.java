import base.BaseTest;
import io.qameta.allure.*;
import org.example.pages.practice.registration.AuthorizationPracticePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("Practice website")
@Feature("Registration")
public class AuthorizationPracticeUniversalTest extends BaseTest {

    public AuthorizationPracticePage authorizationPage;

    @BeforeMethod
    public void newPage() {
        authorizationPage = new AuthorizationPracticePage(webDriver);
        authorizationPage.openPage();
    }

    @DataProvider(name = "users")
    public static Object[][] usersDataProvider() {
        return new Object[][]{
                {"angular", "password", "I am angular!", "You're logged in!!"},
                {"angular", "password", "descriptionSample", "You're logged in!!"},
                {"Not_angular", "password", "Ok, iʼm not angular", "Username or password is incorrect"},
                {"angular", "IncorrectPassword", "description", "Username or password is incorrect"},
                {"4325", "654645", "3245", "Username or password is incorrect"},
                {"password", "angular", "description", "Username or password is incorrect"},
                {"angular", "Password", "", ""},
                {"an", "password", "description", ""},
                {"angular", "pa", "description", ""}
        };
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("Авторизация пользователя")
    @Test(description = "Универсальный тест авторизации", dataProvider = "users")
    public void universalAuthTest(String username, String password, String usernameDescription, String expectedMassage) {
        String actualMassage = authorizationPage.auth(username, password, usernameDescription);
        Assert.assertEquals(actualMassage, expectedMassage,
                "Сообщение об успешной авторизации отличается от ожидаемого");
    }
}
