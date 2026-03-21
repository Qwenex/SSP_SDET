package yandexDiskTests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class RestoreDirYDTest extends BaseYDTest {

    @Severity(SeverityLevel.MINOR)
    @Test(description = "Валидное восстановление директории")
    public void restoreDirectoryTest() {
        String nameDir = "TestDir3";
        RestAssured
                .given(requestSpec)
                .queryParam("path", nameDir)
                .when()
                .put(RESOURCES_PATH)
                .then()
                .statusCode(201);

        RestAssured
                .given(requestSpec)
                .queryParam("path", nameDir)
                .when()
                .delete(RESOURCES_PATH)
                .then()
                .statusCode(204);

        Response trashList = RestAssured
                .given(requestSpec)
                .when()
                .get(TRASH_PATH)
                .then()
                .statusCode(200)
                .extract()
                .response();

        ArrayList<String> trashPathList = new ArrayList<>(trashList.path("_embedded.items.path"));
        String newPathDir = trashPathList.stream()
                .filter(s -> s.startsWith("trash:/" + nameDir))
                .map(s -> s.substring("trash:/".length()))
                .findFirst()
                .orElse(null);

        RestAssured
                .given(requestSpec)
                .queryParam("path", newPathDir)
                .when()
                .put(TRASH_PATH + "restore")
                .then()
                .statusCode(201);
    }
}
