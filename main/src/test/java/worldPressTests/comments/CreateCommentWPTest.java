package worldPressTests.comments;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.example.worldPress.CommentWP;
import org.example.worldPress.PostWP;
import org.example.worldPress.StatusPostWP;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import worldPressTests.BaseWPTest;

import static org.example.worldPress.helpers.ApiWpHelper.*;
import static org.testng.Assert.assertEquals;

@Story("Создание комментария к посту")
public class CreateCommentWPTest extends BaseWPTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Создание нового комментария на опубликованный пост с валидными данными")
    public void createCommentTest() {
        String title = "Пост 1";
        String postContent = "Описание 1";
        StatusPostWP status = StatusPostWP.PUBLISH;
        PostWP postWP = apiWpHelper.createPostWP(title, postContent, status);

        Integer post = postWP.getId();
        String commentContent = "Комментарий 1";
        CommentWP commentWP = apiWpHelper.createCommentWP(post, commentContent);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(commentWP.getContent(), commentContent,
                "Параметр комментария \"content\" отличается от ожидаемого");
        softAssert.assertEquals(commentWP.getPost(), postWP.getId(),
                "Параметр комментария \"Post\" и параметр поста \"ID\" не совпадают");

        CommentWP commentWPFromDB = dbWpHelper.getAllCommentsWPFromDB().get(commentWP.getId() - 1);
        softAssert.assertEquals(commentWPFromDB.getId(), commentWP.getId(),
                "Параметр комментария из БД \"ID\" отличается от ожидаемого");
        softAssert.assertEquals(commentWPFromDB.getPost(), commentWP.getPost(),
                "Параметр комментария из БД \"Post\" отличается от ожидаемого");
        softAssert.assertEquals(commentWPFromDB.getContent(), commentWP.getContent(),
                "Параметр комментария из БД \"content\" отличается от ожидаемого");
        softAssert.assertEquals(commentWPFromDB.getPost(), postWP.getId(),
                "Параметр комментария из БД \"Post\" и параметр поста из БД \"ID\" не совпадают");
        softAssert.assertAll();
    }

    @Severity(SeverityLevel.MINOR)
    @Test(description = "Попытка сделать пустой комментарий к посту (без параметра \"content\")")
    public void createCommentNegativeTest() {
        String title = "Пост без комментариев";
        String postContent = "Описание";
        StatusPostWP status = StatusPostWP.PUBLISH;
        PostWP postWP = apiWpHelper.createPostWP(title, postContent, status);

        Integer post = postWP.getId();
        RestAssured.given()
                .spec(requestSpec)
                .queryParam("rest_route", COMMENTS)
                .queryParam("post", post)
                .when()
                .post()
                .then()
                .statusCode(400);
    }
}
