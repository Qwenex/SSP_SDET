package org.example.worldPress.helpers;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.example.utils.ReadProperty;
import org.example.worldPress.CommentWP;
import org.example.worldPress.PostWP;
import org.example.worldPress.StatusPostWP;

public class ApiWpHelper {

    private final static ReadProperty urlProperty = new ReadProperty("wordPress/wordPressURL");
    private final static String BASE_URL = urlProperty.get("base.url");
    public final static String POSTS = "/wp/v2/posts/";
    public final static String COMMENTS = "/wp/v2/comments/";

    private final static ReadProperty authProperty = new ReadProperty("wordPress/authWP");
    private final static String login = authProperty.get("login");
    private final static String password = authProperty.get("password");

    public static RequestSpecification requestSpec;

    @Step("Создание спецификации")
    public void setSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setAuth(RestAssured.preemptive().basic(login, password))
                .build();
    }

    @Step("Создание нового поста")
    public PostWP createPostWP(String title, String content) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("rest_route", POSTS)
                .queryParam("title", title)
                .queryParam("content", content)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .as(PostWP.class);
    }

    @Step("Создание нового поста")
    public PostWP createPostWP(String title, String content, StatusPostWP status) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("rest_route", POSTS)
                .queryParam("title", title)
                .queryParam("content", content)
                .queryParam("status", status)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .as(PostWP.class);
    }

    @Step("Получение поста")
    public PostWP getPostWP(Integer id) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("rest_route", POSTS + id)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .as(PostWP.class);
    }

    @Step("Изменение поста")
    public PostWP updatePostWP(Integer id, String title, String content, StatusPostWP status) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("rest_route", POSTS + id)
                .queryParam("title", title)
                .queryParam("content", content)
                .queryParam("status", status)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .as(PostWP.class);
    }

    @Step("Удаление поста")
    public PostWP deletePostWP(Integer id) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("rest_route", POSTS + id)
                .when()
                .delete()
                .then()
                .statusCode(200)
                .extract()
                .as(PostWP.class);
    }

    @Step("Создание нового комментария")
    public CommentWP createCommentWP(Integer post, String content) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("rest_route", COMMENTS)
                .queryParam("post", post)
                .queryParam("content", content)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .as(CommentWP.class);
    }

    @Step("Получение комментария")
    public CommentWP getCommentWP(Integer id) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("rest_route", COMMENTS + id)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .as(CommentWP.class);
    }

    @Step("Изменение комментария")
    public CommentWP updateCommentWP(Integer id, String content) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("rest_route", COMMENTS + id)
                .queryParam("content", content)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .as(CommentWP.class);
    }

    @Step("Удаление комментария")
    public CommentWP deleteCommentWP(Integer id) {
        return RestAssured.given()
                .spec(requestSpec)
                .queryParam("rest_route", COMMENTS + id)
                .when()
                .delete()
                .then()
                .statusCode(200)
                .extract()
                .as(CommentWP.class);
    }
}
