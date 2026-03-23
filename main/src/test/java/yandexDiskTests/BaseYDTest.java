package yandexDiskTests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.example.yandexDisk.helpers.ApiYdHelper;
import org.testng.annotations.BeforeClass;

@Epic("API тесты")
@Feature("Тестирование Yandex Disk")
public class BaseYDTest {

    public ApiYdHelper apiYdHelper = new ApiYdHelper();

    @BeforeClass
    public void setUp() {
        apiYdHelper.setSpec();
    }
}
