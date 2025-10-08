import io.qameta.allure.*;
import org.example.page.practiceAutomation.FormFieldsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;

public class FormFieldsTest {

    WebDriver webDriver = new ChromeDriver();
    FormFieldsPage formFieldsPage = new FormFieldsPage(webDriver);

    @BeforeMethod
    public void openPage() {
        formFieldsPage.openPage();
    }

    @AfterMethod
    public void closePage() {
        formFieldsPage.closePage();
    }

    @Epic("Сайт practice-automation.com")
    @Feature("Страница Form Fields")
    @Story("Функционал отправки формы")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Заполнение всех форм и полей под требуемые условия," +
            " нажатие кнопки отправки и ожидание алерт-сообщения об успехе отправки формы")
    @Test(description = "Получение алерт-сообщения \"Message received!\"")
    public void pageTest() {
        String massageExpected = "Message received!";
        FormFieldsPage page = formFieldsPage
                .setName("Andrew")
                .setPassword("qwerty123")
                .pickFavoriteDrink("Milk", "Coffee")
                .pickFavoriteColor("Yellow")
                .pickDoYouLikeAutomation("Yes")
                .setEmail("myEmail@gmail.com");
        Integer numberOfTools = page.getNumberOfTools();
        String toolWithMaxLength = page.getToolsList().stream().max(Comparator.comparingInt(String::length)).orElse("");
        String massageActual = page
                .setMassageText(String.format("%s, %s", numberOfTools, toolWithMaxLength))
                .clickSubmitButton()
                .getTextFromAlert();

        Assert.assertEquals(massageActual, massageExpected);
    }
}

