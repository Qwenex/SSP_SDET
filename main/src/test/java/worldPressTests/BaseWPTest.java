package worldPressTests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.example.worldPress.helpers.ApiWpHelper;
import org.example.worldPress.helpers.DbWpHelper;
import org.testng.annotations.BeforeClass;

@Epic("API тесты")
@Feature("Тестирование World Press")
public class BaseWPTest {

    public ApiWpHelper apiWpHelper = new ApiWpHelper();
    public DbWpHelper dbWpHelper = new DbWpHelper();

    @BeforeClass
    public void setUp() {
        apiWpHelper.setSpec();
    }
}
