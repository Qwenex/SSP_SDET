import base.BaseTest;
import io.qameta.allure.*;
import org.example.pages.practice.bankingApp.BankManagerLoginPage;
import org.example.pages.practice.bankingApp.CustomerLoginPage;
import org.example.pages.practice.bankingApp.HomePage;
import org.example.pages.practice.bankingApp.SampleFormPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Comparator;
import java.util.Random;

@Epic("Way2Automation Banking App")
public class BankingAppTest extends BaseTest {

    public HomePage homePage;
    public SoftAssert softAssert;

    @BeforeMethod
    public void newPage() {
        homePage = new HomePage(webDriver);
        homePage.openPage();
    }

    // 5.1
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Страница \"Sample Form\"")
    @Story("Регистрация клиента")
    @Description("Валидная регистрация клиента с вводом в поле \"About Yourself\" самое длинное слово из \"Hobbies\"")
    @Test(description = "Валидная регистрация клиента")
    public void validRegisterInSampleFormTest() {
        SampleFormPage sampleFormPage = homePage.moveToSampleFormPage();
        String hobbyWithMaxLength = sampleFormPage.getHobbiesList()
                .stream().max(Comparator.comparingInt(String::length)).orElse("");

        String actualMessage = sampleFormPage
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setEmail("myEmail@gmail.com")
                .setPassword("myPassword")
                .setGender("Male")
                .setHobbies("Sports")
                .setAboutYourself("Самое длинное слово из предложенных хобби - " + hobbyWithMaxLength)
                .submit();
        String expectedMessage = "User registered successfully!";

        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @DataProvider(name = "customerForTest")
    public static Object[][] customerForTestDataProvider() {
        return new Object[][]{
                {"Maria", "Solovyova", "E1234A"}
        };
    }

    // 5.2.1
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Страница \"Bank Manager Login\"")
    @Story("Добавление клиента")
    @Description("Валидное добавление нового клиента")
    @Test(description = "Валидное добавление клиента", dataProvider = "customerForTest")
    public void addCustomerInBankManagerLoginTest(String firstName, String lastName, String postCode) {
        String actualMessage = homePage
                .moveToBankManagerLoginPage()
                .addCustomerAndGetAlert(firstName, lastName, postCode);
        String expectedMessage = "Customer added successfully";

        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Alert сообщение отличается от ожидаемого");
    }

    // 5.2.2
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Страница \"Bank Manager Login\"")
    @Story("Открытие аккаунта клиента")
    @Description("Валидное открытие аккаунта клиента")
    @Test(description = "Валидное открытие аккаунта клиента", dataProvider = "customerForTest")
    public void openCustomerInBankManagerLoginTest(String firstName, String lastName, String postCode) {
        BankManagerLoginPage bankManagerLoginPage = homePage.moveToBankManagerLoginPage();

        String actualAddCustomerAlert = bankManagerLoginPage
                .addCustomerAndGetAlert(firstName, lastName, postCode);
        String expectedAddCustomerAlert = "Customer added successfully";
        Assert.assertTrue(actualAddCustomerAlert.contains(expectedAddCustomerAlert),
                "Alert сообщение отличается от ожидаемого");

        String actualMessage = bankManagerLoginPage.openAccountAndGetAlert(
                String.format("%s %s", firstName, lastName), "Dollar");
        String expectedMessage = "Account created successfully";
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Alert сообщение отличается от ожидаемого");
    }

    // 5.3
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Страница \"Customer Login\"")
    @Story("Набор операций со счетом клиента")
    @Description("Набор операций со счетом клиента: Пополнение, снятие, проверка баланса, очистка транзакций")
    @Test(description = "Набор операций со счетом клиента", dataProvider = "customerForTest")
    public void customerTest(String firstName, String lastName, String postCode) {
        String customer = String.format("%s %s", firstName, lastName);
        BankManagerLoginPage bankManagerLoginPage = homePage.moveToBankManagerLoginPage();

        String actualAddCustomerAlert = bankManagerLoginPage.addCustomerAndGetAlert(firstName, lastName, postCode);
        String expectedAddCustomerAlert = "Customer added successfully";
        Assert.assertTrue(actualAddCustomerAlert.contains(expectedAddCustomerAlert),
                "Alert сообщение отличается от ожидаемого");

        String actualOpenAccountAlert = bankManagerLoginPage.openAccountAndGetAlert(customer, "Dollar");
        String expectedOpenAccountAlert = "Account created successfully";
        Assert.assertTrue(actualOpenAccountAlert.contains(expectedOpenAccountAlert),
                "Alert сообщение отличается от ожидаемого");

        CustomerLoginPage customerLoginPage = bankManagerLoginPage
                .moveToHomePage()
                .moveToCustomerLoginPage();

        String actualMessage = customerLoginPage
                .selectCustomer(customer)
                .loginButtonClickAndGetMassage();
        String expectedMessage = String.format("Welcome %s !!", customer);
        Assert.assertEquals(actualMessage, expectedMessage);

        softAssert = new SoftAssert();

        // 5.3.1
        Integer depositAmount = 100321;
        String actualMessage1 = customerLoginPage.enterDeposit(depositAmount);
        String expectedMessage1 = "Deposit Successful";
        softAssert.assertEquals(actualMessage1, expectedMessage1,
                "Сообщение об успешном внесении депозита должно появится");
        softAssert.assertTrue(customerLoginPage.isAmountInCreditList(depositAmount),
                "Данные в транзакциях отличаются от ожидаемых");

        // 5.3.2
        Integer zeroDepositAmount = 0;
        String actualMessage2 = customerLoginPage.enterDeposit(zeroDepositAmount);
        String expectedMessage2 = "";
        softAssert.assertEquals(actualMessage2, expectedMessage2,
                "Сообщение об успешном внесении депозита НЕ должно появится");
        softAssert.assertFalse(customerLoginPage.isAmountInCreditList(zeroDepositAmount),
                "Данные в транзакциях отличаются от ожидаемых");

        // 5.3.3
        Integer customerBalance = customerLoginPage.getCustomerBalance();
        Random rnd = new Random();
        Integer rndWithdrawnAmount = rnd.nextInt(customerBalance) + 1;
        String actualMessage3 = customerLoginPage.takeWithdrawn(rndWithdrawnAmount);
        String expectedMessage3 = "Transaction successful";
        softAssert.assertEquals(actualMessage3, expectedMessage3,
                "Сообщение об успешной транзакции должно появится");
        softAssert.assertTrue(customerLoginPage.isAmountInDebitList(rndWithdrawnAmount),
                "Данные в транзакциях отличаются от ожидаемых");

        // 5.3.4
        Integer withdrawnAmount = 1000000;
        String actualMessage4 = customerLoginPage.takeWithdrawn(withdrawnAmount);
        String expectedMessage4 = "Transaction Failed. You can not withdraw amount more than the balance.";
        softAssert.assertEquals(actualMessage4, expectedMessage4,
                "Сообщение об НЕ успешной транзакции должно появится");
        softAssert.assertFalse(customerLoginPage.isAmountInDebitList(withdrawnAmount),
                "Данные в транзакциях отличаются от ожидаемых");

        // 5.3.5
        Integer actualMessage5 = customerLoginPage.getCustomerBalance();
        Integer expectedMessage5 = customerLoginPage.getCustomerBalanceFromTransactions();
        softAssert.assertEquals(actualMessage5,expectedMessage5,
                "Баланс, рассчитанный из транзакций отличается от баланса счета");

        // 5.3.6
        Integer customerBalance1 = customerLoginPage.getCustomerBalance();
        customerLoginPage.takeWithdrawn(customerBalance1);
        Integer actualMessage6 = customerLoginPage.getCustomerBalance();
        Integer expectedMessage6 = 0;
        softAssert.assertEquals(actualMessage6, expectedMessage6,
                "Баланс должен быть равен 0");

        // 5.3.7
        Integer expectedTransactions = 3;
        boolean actualMessage7 = customerLoginPage.equalsCountTransaction(expectedTransactions);
        softAssert.assertTrue(actualMessage7,
                "Изначальное количество транзакций должно быть больше 0");

        customerLoginPage.resetTransactions();

        Integer expectedTransactions1 = 0;
        boolean actualMessage8 = customerLoginPage.equalsCountTransaction(expectedTransactions1);
        softAssert.assertTrue(actualMessage8,
                "Количество транзакций должно быть равно 0");

        Integer expectedMessage9 = 0;
        Integer actualMessage9 = customerLoginPage.getCustomerBalance();
        softAssert.assertEquals(actualMessage9, expectedMessage9,
                "Баланс должен быть равен 0");

        softAssert.assertAll();
    }

    // 5.4
    @Severity(SeverityLevel.NORMAL)
    @Feature("Страница \"Bank Manager Login\"")
    @Story("Удаление клиента")
    @Description("Удаление клиента и проверка в таблице \"Customers\"")
    @Test(description = "Удаление клиента")
    public void deleteCustomerForFirstNameTest() {
        String customerFirstName = "customerForDelete";
        BankManagerLoginPage bankManagerLoginPage = homePage.moveToBankManagerLoginPage();

        String actualAddCustomerAlert = bankManagerLoginPage
                .addCustomerAndGetAlert(customerFirstName, "lastName", "A1111");
        String expectedAddCustomerAlert = "Customer added successfully";
        Assert.assertTrue(actualAddCustomerAlert.contains(expectedAddCustomerAlert),
                "Alert сообщение отличается от ожидаемого");

        bankManagerLoginPage.moveToCustomersList();
        Assert.assertTrue(bankManagerLoginPage.isCustomerExist(customerFirstName),
                String.format("Клиент с именем \"%s\" должен быть найден в таблице", customerFirstName));

        bankManagerLoginPage.deleteCustomer(customerFirstName).clearSearch();
        Assert.assertFalse(bankManagerLoginPage.isCustomerExist(customerFirstName),
                String.format("Клиент с именем \"%s\" не должен быть найден в таблице", customerFirstName));
    }
}
