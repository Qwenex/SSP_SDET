package yandexDiskTests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Story("Получение общей информации о пространстве Yandex Disk")
public class GetInfoYDTest extends BaseYDTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Проверка логина и отображаемого имени пользователя")
    public void getInfoPTest() {
        Response response = RestAssured
                .given(requestSpec)
                .when()
                .get(DISK_PATH)
                .then()
                .spec(responseGetSpec)
                .extract()
                .response();

        String actualLogin = response.path("user.login");
        String expectedLogin = "qwenex";
        assertEquals(actualLogin, expectedLogin,
                "Параметр пользователя \"login\" не совпадает с ожидаемым");

        String actualDisplayName = response.path("user.display_name");
        String expectedDisplayName = "qwenex";
        assertEquals(actualDisplayName, expectedDisplayName,
                "Параметр пользователя \"display_name\" не совпадает с ожидаемым");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Попытка получения информации о пространстве Yandex Disk без токена авторизации")
    public void getInfoNTest() {
        Response response = RestAssured
                .given()
                .baseUri(BASE_URL)
                .when()
                .get(DISK_PATH)
                .then()
                .statusCode(401)
                .extract()
                .response();

        assertNotNull(response.path("error"));
        assertNotNull(response.path("description"));
        assertNotNull(response.path("message"));
    }
}
