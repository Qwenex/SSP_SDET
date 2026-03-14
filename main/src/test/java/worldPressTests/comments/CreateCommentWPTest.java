package worldPressTests.comments;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.example.worldPressPojo.CommentWP;
import org.example.worldPressPojo.PostWP;
import org.example.worldPressPojo.StatusPostWP;
import org.testng.annotations.Test;
import worldPressTests.BaseWPTest;

import static org.testng.Assert.assertEquals;

@Story("Создание комментария к посту")
public class CreateCommentWPTest extends BaseWPTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Создание нового комментария на опубликованный пост с валидными данными")
    public void createCommentTest() {
        String title = "Пост 1";
        String postContent = "Описание 1";
        StatusPostWP status = StatusPostWP.PUBLISH;
        PostWP postWP = createPostWP(title, postContent, status);

        Integer post = postWP.getId();
        String commentContent = "Комментарий 1";
        CommentWP commentWP = createCommentWP(post, commentContent);

        assertEquals(commentWP.getContent(), commentContent,
                "Параметр комментария \"content\" отличается от ожидаемого");
        assertEquals(commentWP.getPost(), postWP.getId(),
                "Параметр комментария \"Post\" и параметр поста \"ID\" не совпадают");

        CommentWP commentWPFromDB = getAllCommentsWPFromDB().get(commentWP.getId() - 1);
        assertEquals(commentWPFromDB.getId(), commentWP.getId(),
                "Параметр комментария из БД \"ID\" отличается от ожидаемого");
        assertEquals(commentWPFromDB.getPost(), commentWP.getPost(),
                "Параметр комментария из БД \"Post\" отличается от ожидаемого");
        assertEquals(commentWPFromDB.getContent(), commentWP.getContent(),
                "Параметр комментария из БД \"content\" отличается от ожидаемого");
        assertEquals(commentWPFromDB.getPost(), postWP.getId(),
                "Параметр комментария из БД \"Post\" и параметр поста из БД \"ID\" не совпадают");
    }

    @Severity(SeverityLevel.MINOR)
    @Test(description = "Попытка сделать пустой комментарий к посту (без параметра \"content\")")
    public void createCommentNegativeTest() {
        String title = "Пост без комментариев";
        String postContent = "Описание";
        StatusPostWP status = StatusPostWP.PUBLISH;
        PostWP postWP = createPostWP(title, postContent, status);

        Integer post = postWP.getId();
        RestAssured.given()
                .auth().preemptive().basic(login, password)
                .baseUri(BASE_URL)
                .queryParam("rest_route", COMMENTS)
                .queryParam("post", post)
                .when()
                .post()
                .then()
                .statusCode(400);
    }
}
