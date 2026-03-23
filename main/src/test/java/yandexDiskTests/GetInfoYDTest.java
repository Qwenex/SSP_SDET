package yandexDiskTests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.example.yandexDisk.helpers.ApiYdHelper.*;
import static org.hamcrest.Matchers.*;

@Story("Получение общей информации о пространстве Yandex Disk")
public class GetInfoYDTest extends BaseYDTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка логина и отображаемого имени пользователя")
    public void getInfoPTest() {
        RestAssured
                .given(requestSpec)
                .when()
                .get(BASE_PATH)
                .then()
                .spec(responseGetSpec)
                .body("user.login", equalTo("qwenex"))
                .body("user.display_name", equalTo("qwenex"));
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Попытка получения информации о пространстве Yandex Disk без токена авторизации")
    public void getInfoNTest() {
        RestAssured
                .given()
                .baseUri(BASE_URL)
                .when()
                .get(BASE_PATH)
                .then()
                .statusCode(401)
                .body("error", is(notNullValue()))
                .body("description", is(notNullValue()))
                .body("message", is(notNullValue()));
    }
}
