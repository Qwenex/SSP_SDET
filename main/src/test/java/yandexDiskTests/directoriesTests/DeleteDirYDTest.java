package yandexDiskTests.directoriesTests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.testng.annotations.Test;
import yandexDiskTests.BaseYDTest;

import static org.example.yandexDisk.helpers.ApiYdHelper.RESOURCES_PATH;
import static org.example.yandexDisk.helpers.ApiYdHelper.requestSpec;

@Story("Удаление директории")
public class DeleteDirYDTest extends BaseYDTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Валидное удаление директории")
    public void deleteDirectoryTest() {
        String nameDir = "TestDir2";
        apiYdHelper.createDirectory(nameDir);

        RestAssured
                .given(requestSpec)
                .queryParam("path",nameDir)
                .when()
                .delete(RESOURCES_PATH)
                .then()
                .statusCode(204);
    }
}
