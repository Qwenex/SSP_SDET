import io.qameta.allure.*;
import org.example.page.manager.ListCustomerPage;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Epic("Сайт XYZ Bank")
@Feature("Manager login")
@Story("Customer List")
public class ListCustomerTest extends BaseTest {

    ListCustomerPage listCustomerPage;

    @BeforeMethod
    public void openPage() {
        listCustomerPage = new ListCustomerPage(webDriver);
    }

    @Severity(SeverityLevel.MINOR)
    @Description("Получение сортированного списка имен клиентов в алфовитном порядке для текущего списка")
    @Test(description = "Получение отсортированного списка имен клиентов")
    public void getSortedFirstNames() {
        List<String> names = listCustomerPage
                .openPage()
                .getNames();
        List<String> expectedList = new ArrayList<>(names);
        expectedList.sort(Comparator.naturalOrder());

        List<String> actualList = listCustomerPage
                .getSortedNamesByNaturalOrderList();

        Assert.assertFalse(names.isEmpty(), "Список имен в таблице пустой");
        Assert.assertEquals(actualList, expectedList, "Отсортированный список отличается от ожидаемого");
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Удаление клиента с именем, длина которого ближе всего к среднему арифметичскому")
    @Test(description = "Удаление клиента с именем средней длинны")
    public void deleteCustomerWithAverageLengthName() {
        List<String> names = listCustomerPage
                .openPage()
                .getNames();
        String nameWithAverageLength = listCustomerPage.nameWithAverageLength();

        List<String> expectedList = new ArrayList<>(names);
        expectedList.remove(nameWithAverageLength);

        listCustomerPage.deleteCustomer(nameWithAverageLength);
        List<String> actualList = listCustomerPage.getNames();

        Assert.assertFalse(names.isEmpty(), "Список имен в таблице пустой");
        Assert.assertEquals(actualList, expectedList, "Список имен отличается от ожидаемого");
    }
}
