package worldPressTests.posts;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.example.worldPressPojo.PostWP;
import org.testng.annotations.Test;
import worldPressTests.BaseWPTest;

import static org.testng.Assert.assertEquals;

@Story("Создание поста")
public class CreatePostWPTest extends BaseWPTest {

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Создание нового поста с валидными данными")
    public void createPostTest() {
        String title = "Пост 1";
        String content = "Описание 1";
        PostWP postWP = createPostWP(title, content);

        assertEquals(postWP.getTitle(), title,
                "Параметр поста \"title\" отличается от ожидаемого");
        assertEquals(postWP.getContent(), content,
                "Параметр поста \"content\" отличается от ожидаемого");

        PostWP postWPFromDB = getAllPostsWPFromDB().get(postWP.getId() - 1);
        assertEquals(postWPFromDB.getId(), postWP.getId(),
                "Параметр поста из БД \"ID\" отличается от ожидаемого");
        assertEquals(postWPFromDB.getTitle(), postWP.getTitle(),
                "Параметр поста из БД \"title\" отличается от ожидаемого");
        assertEquals(postWPFromDB.getContent(), postWP.getContent(),
                "Параметр поста из БД \"content\" отличается от ожидаемого");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Попытка создания нового поста без параметров \"title\" и \"content\"")
    public void createPostNegativeTest() {
        Object actualMessage = RestAssured.given()
                .auth().preemptive().basic(login, password)
                .baseUri(BASE_URL)
                .queryParam("rest_route", POSTS)
                .when()
                .post()
                .then()
                .statusCode(400)
                .extract()
                .path("message");
        String expectedMessage = "Содержимое, заголовок и отрывок пусты.";
        assertEquals(actualMessage, expectedMessage,
                "Выводимое сообщение отличается от ожидаемого");
    }
}
