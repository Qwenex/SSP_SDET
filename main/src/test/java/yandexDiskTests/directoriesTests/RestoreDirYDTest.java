package yandexDiskTests.directoriesTests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.testng.annotations.Test;
import yandexDiskTests.BaseYDTest;

import static org.example.yandexDisk.helpers.ApiYdHelper.TRASH_PATH;
import static org.example.yandexDisk.helpers.ApiYdHelper.requestSpec;

@Story("Восстановление папки после удаления")
public class RestoreDirYDTest extends BaseYDTest {

    @Severity(SeverityLevel.MINOR)
    @Test(description = "Валидное восстановление директории")
    public void restoreDirectoryTest() {
        String nameDir = "TestDir3";
        apiYdHelper.createDirectory(nameDir);
        apiYdHelper.deleteDirectory(nameDir);
        String newPathDir = apiYdHelper.getDeletedFilePath(nameDir);

        RestAssured
                .given(requestSpec)
                .queryParam("path", newPathDir)
                .when()
                .put(TRASH_PATH + "restore")
                .then()
                .statusCode(201);
    }
}
