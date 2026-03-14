package worldPressTests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.example.utils.ConnectMySqlDB;
import org.example.utils.ReadProperty;
import org.example.worldPressPojo.CommentWP;
import org.example.worldPressPojo.PostWP;
import org.example.worldPressPojo.StatusCommentWP;
import org.example.worldPressPojo.StatusPostWP;

import java.util.List;

@Epic("API тесты")
@Feature("Тестирование World Press")
public class BaseWPTest {

    protected final static String BASE_URL = "http://localhost:8000";
    protected final static String POSTS = "/wp/v2/posts/";
    protected final static String COMMENTS = "/wp/v2/comments/";

    private final static String SQL_POSTS_REQUEST =
            "SELECT ID, post_title, post_content, post_status FROM wp_posts ";
    private final static String SQL_COMMENTS_REQUEST =
            "SELECT comment_id, comment_post_ID, comment_content, comment_approved FROM wp_comments ";

    private final static ReadProperty authProperty = new ReadProperty("authWP");
    protected final static String login = authProperty.get("login");
    protected final static String password = authProperty.get("password");

    @Step("Создание нового поста")
    public PostWP createPostWP(String title, String content) {
        return RestAssured.given()
                .auth().preemptive().basic(login, password)
                .baseUri(BASE_URL)
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
                .auth().preemptive().basic(login, password)
                .baseUri(BASE_URL)
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
                .auth().preemptive().basic(login, password)
                .baseUri(BASE_URL)
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
                .auth().preemptive().basic(login, password)
                .baseUri(BASE_URL)
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
                .auth().preemptive().basic(login, password)
                .baseUri(BASE_URL)
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
                .auth().preemptive().basic(login, password)
                .baseUri(BASE_URL)
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
                .auth().preemptive().basic(login, password)
                .baseUri(BASE_URL)
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
                .auth().preemptive().basic(login, password)
                .baseUri(BASE_URL)
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
                .auth().preemptive().basic(login, password)
                .baseUri(BASE_URL)
                .queryParam("rest_route", COMMENTS + id)
                .when()
                .delete()
                .then()
                .statusCode(200)
                .extract()
                .as(CommentWP.class);
    }

    @Step("Получение постов из базы данных")
    public List<PostWP> getAllPostsWPFromDB() {
        return ConnectMySqlDB.getData(
                SQL_POSTS_REQUEST + "ORDER BY ID",
                rs -> new PostWP(
                        rs.getInt("ID"),
                        rs.getString("post_title"),
                        rs.getString("post_content"),
                        StatusPostWP.fromString(rs.getString("post_status"))
                )
        );
    }

    @Step("Получение комментариев из базы данных")
    public List<CommentWP> getAllCommentsWPFromDB() {
        return ConnectMySqlDB.getData(
                SQL_COMMENTS_REQUEST + "ORDER BY comment_id",
                rs -> {
                    String dbStatus = rs.getString("comment_approved");
                    StatusCommentWP status = "1".equals(dbStatus) ?
                            StatusCommentWP.APPROVED : StatusCommentWP.fromString(dbStatus);
                    return new CommentWP(
                            rs.getInt("comment_id"),
                            rs.getInt("comment_post_ID"),
                            rs.getString("comment_content"),
                            status
                    );
                }
        );
    }
}
