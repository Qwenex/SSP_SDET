package yandexDiskTests.filesTests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.example.utils.ReadProperty;
import org.example.yandexDisk.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import yandexDiskTests.BaseYDTest;

import static org.example.yandexDisk.helpers.ApiYdHelper.*;

@Story("Скачивание файла с диска")
public class DownloadFileYDTest extends BaseYDTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Скачивание файла с диска")
    public void downloadTest() {
        ReadProperty property = new ReadProperty("yandexApi/userForTest");
        User user = new User(property.get("username"), property.get("password"));

        String dirName = "sdet_data";
        String path = dirName + "/data.txt";
        apiYdHelper.deleteFile(dirName);
        apiYdHelper.createDirectory(dirName);

        apiYdHelper.uploadFile(user, path);

        String href = RestAssured
                .given(requestSpec)
                .queryParam("path", path)
                .when()
                .get(RESOURCES_PATH + "download")
                .then()
                .statusCode(200)
                .extract()
                .path("href")
                .toString();

        User userFromDisk = RestAssured
                .given(noEncoding)
                .when()
                .get(href)
                .then()
                .spec(responseGetSpec)
                .extract()
                .as(User.class);

        Assert.assertEquals(userFromDisk, user,
                "Данные пользователя отличаются от ожидаемых");
        apiYdHelper.deleteFile(dirName);
    }
}
