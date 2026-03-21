package yandexDiskTests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

@Story("Создание новой директории")
public class CreateDirYDTest extends BaseYDTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Валидное создание новой директории")
    public void createDirectoryPTest() {
        RestAssured
                .given(requestSpec)
                .queryParam("path","TestDir1")
                .when()
                .put(RESOURCES_PATH)
                .then()
                .statusCode(201);
    }

    @Severity(SeverityLevel.MINOR)
    @Test(description = "Попытка создать директорию без названия")
    public void createDirectoryNTest() {
        String expectedMessage = "Ошибка проверки поля \"path\": Это поле является обязательным.";
        RestAssured
                .given(requestSpec)
                .when()
                .put(RESOURCES_PATH)
                .then()
                .statusCode(400)
                .body("message", is(expectedMessage));
    }
}
