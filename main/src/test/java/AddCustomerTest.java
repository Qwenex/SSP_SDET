import io.qameta.allure.*;
import org.example.page.manager.AddCustomerPage;
import org.example.utilite.GenerationCustomer;
import org.testng.Assert;
import org.testng.annotations.*;

@Epic("Сайт XYZ Bank")
@Feature("Manager login")
@Story("Add Customer")
public class AddCustomerTest extends BaseTest {

    AddCustomerPage addCustomerPage;

    @BeforeMethod
    public void openPage() {
        addCustomerPage = new AddCustomerPage(webDriver);
    }

    @DataProvider(name = "randomGeneratedCustomer")
    public static Object[][] randomCustomerDataProvider() {
        return new Object[][]{
                {new GenerationCustomer()},
                {new GenerationCustomer()},
                {new GenerationCustomer()}
        };
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("Добавление нового клиента заполнив все поля под требуемые условия" +
            " нажатие кнопки добавления и ожидание Alert сообщения об успехе операции")
    @Test(description = "Добавление рандомно-сгенерированного клиента", dataProvider ="randomGeneratedCustomer")
    public void addRandomGenCustomerTest(GenerationCustomer customer) {
        String expectedMassage = "Customer added successfully";
        String actualMassage = addCustomerPage
                .openPage()
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setPostCode(customer.getPostCode())
                .clickSubmitButton()
                .getTextFromAlert();

        Assert.assertTrue(actualMassage.contains(expectedMassage),
                "Алерт сообшение отличается от ожидаемого");
    }
}
