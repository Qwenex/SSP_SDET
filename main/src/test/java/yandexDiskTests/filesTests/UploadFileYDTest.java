package yandexDiskTests.filesTests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.example.utils.ReadProperty;
import org.example.yandexDisk.User;
import org.testng.annotations.Test;
import yandexDiskTests.BaseYDTest;

import static org.example.yandexDisk.helpers.ApiYdHelper.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Story("Загрузка файла на диск")
public class UploadFileYDTest extends BaseYDTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Загрузка файла на диск и его копирование")
    public void uploadAndCopyTest() throws InterruptedException {
        ReadProperty property = new ReadProperty("yandexApi/userForTest");
        User user = new User(property.get("username"), property.get("password"));

        String dirInName = "input_data";
        String dirOutName = "output_data";

        apiYdHelper.deleteFile(dirInName);
        apiYdHelper.deleteFile(dirOutName);
        apiYdHelper.createDirectory(dirInName);

        String href = RestAssured
                .given(requestSpec)
                .queryParam("path", dirInName + "/data.txt")
                .when()
                .get(RESOURCES_PATH + "upload")
                .then()
                .statusCode(200)
                .extract()
                .path("href")
                .toString();

        RestAssured
                .given(requestSpec)
                .contentType("application/json")
                .body(user)
                .when()
                .put(href)
                .then()
                .statusCode(201);

        RestAssured
                .given(requestSpec)
                .queryParam("from", dirInName)
                .queryParam("path", dirOutName)
                .when()
                .post(RESOURCES_PATH + "copy")
                .then()
                .statusCode(202);

        Thread.sleep(1000);
        RestAssured
                .given(requestSpec)
                .queryParam("from", dirInName)
                .queryParam("path", dirOutName)
                .when()
                .post(RESOURCES_PATH + "copy")
                .then()
                .statusCode(409)
                .body("error", is(notNullValue()))
                .body("description", is(notNullValue()))
                .body("message", is(notNullValue()));

        apiYdHelper.deleteFile(dirInName);
        apiYdHelper.deleteFile(dirOutName);
    }
}
