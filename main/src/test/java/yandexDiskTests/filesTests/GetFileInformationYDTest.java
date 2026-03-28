package yandexDiskTests.filesTests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.testng.annotations.Test;
import yandexDiskTests.BaseYDTest;

import static org.example.yandexDisk.helpers.ApiYdHelper.*;

@Story("Получение информации о файле на диске")
public class GetFileInformationYDTest extends BaseYDTest {

    @Severity(SeverityLevel.MINOR)
    @Test(description = "Валидация структуры ответа при запросе информации об файле")
    public void fileShemaValidationTest() {
        String fileName = "file.txt";
        apiYdHelper.deleteFile(fileName);
        apiYdHelper.uploadFile("text", fileName);

        RestAssured
                .given(requestSpec)
                .queryParam("path", fileName)
                .when()
                .get(RESOURCES_PATH)
                .then()
                .spec(responseGetSpec)
                .body(apiYdHelper.matchJsonSchema("getFileInfo"));

        apiYdHelper.deleteFile(fileName);
    }

    @Severity(SeverityLevel.MINOR)
    @Test(description = "Валидация структуры ответа при запросе информации об директории")
    public void directoryShemaValidationTest() {
        String dirName = "dirExample";
        apiYdHelper.deleteFile(dirName);
        apiYdHelper.createDirectory(dirName);

        RestAssured
                .given(requestSpec)
                .queryParam("path", dirName)
                .when()
                .get(RESOURCES_PATH)
                .then()
                .spec(responseGetSpec)
                .body(apiYdHelper.matchJsonSchema("getDirectoryInfo"));

        apiYdHelper.deleteFile(dirName);
    }
}
