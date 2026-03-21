package yandexDiskTests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

@Story("Удаление директории")
public class DeleteDirYDTest extends BaseYDTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Валидное удаление директории")
    public void deleteDirectoryTest() {
        String nameDir = "TestDir2";
        RestAssured
                .given(requestSpec)
                .queryParam("path",nameDir)
                .when()
                .put(RESOURCES_PATH)
                .then()
                .statusCode(201);

        RestAssured
                .given(requestSpec)
                .queryParam("path",nameDir)
                .when()
                .delete(RESOURCES_PATH)
                .then()
                .statusCode(204);
    }
}
