package org.example.yandexDisk.helpers;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.example.utils.ReadProperty;

import java.util.ArrayList;

public class ApiYdHelper {

    private final static ReadProperty urlProperty = new ReadProperty("yandexApi/yandexApiURL");
    public final static String BASE_URL = urlProperty.get("base.url");
    public final static String BASE_PATH = "/v1/disk/";
    public final static String RESOURCES_PATH = BASE_PATH + "resources/";
    public final static String TRASH_PATH = BASE_PATH + "trash/resources/";

    private final static ReadProperty authProperty = new ReadProperty("yandexApi/authYD");
    private final static String AUTH_TOKEN = authProperty.get("OAuth.token");

    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseGetSpec;

    @Step("Создание спецификации")
    public void setSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .build().header("Authorization", AUTH_TOKEN);

        responseGetSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .build();
    }

    @Step("Создание директории")
    public void createDirectory(String path) {
        RestAssured
                .given(requestSpec)
                .queryParam("path",path)
                .when()
                .put(RESOURCES_PATH)
                .then()
                .statusCode(201);
    }

    @Step("Удаление файла или директории")
    public void deleteDirectory(String path) {
        RestAssured
                .given(requestSpec)
                .queryParam("path", path)
                .when()
                .delete(RESOURCES_PATH)
                .then()
                .statusCode(204);
    }

    @Step("Получение списка файлов в корзине")
    public Response getTrashList() {
        return RestAssured
                .given(requestSpec)
                .when()
                .get(TRASH_PATH)
                .then()
                .spec(responseGetSpec)
                .extract()
                .response();
    }

    @Step("Получение информации о файле")
    public void getFileInfo(String path) {
        RestAssured
                .given(requestSpec)
                .queryParam("path",path)
                .when()
                .get(RESOURCES_PATH)
                .then()
                .spec(responseGetSpec);
    }

    @Step("Получение имени удаленного файла")
    public String getDeletedFilePath(String path) {
        ArrayList<String> trashPathList = new ArrayList<>(getTrashList().path("_embedded.items.path"));
        return trashPathList.stream()
                .filter(s -> s.startsWith("trash:/" + path))
                .map(s -> s.substring("trash:/".length()))
                .findFirst()
                .orElse(null);
    }

    @Step("Восстановление файла из корзины")
    public void restoreDirectory(String path) {
        RestAssured
                .given(requestSpec)
                .queryParam("path", path)
                .when()
                .put(TRASH_PATH + "restore")
                .then()
                .statusCode(201);
    }
}
