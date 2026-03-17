package worldPressTests.posts;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.worldPress.PostWP;
import org.example.worldPress.StatusPostWP;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import worldPressTests.BaseWPTest;

@Story("Получение поста")
public class GetPostWPTest extends BaseWPTest {

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Получение поста через Api созданного через БД")
    public void getPostTest() {
        String title = "Пост на получение по API";
        String content = "Описание поста на получения";
        StatusPostWP status = StatusPostWP.PENDING;
        PostWP postWPDB = dbWpHelper.setPostWP(title, content, status);
        Integer postId = postWPDB.getId();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(postWPDB.getTitle(), title,
                "Параметр поста в БД \"title\" отличается от ожидаемого");
        softAssert.assertEquals(postWPDB.getContent(), content,
                "Параметр поста в БД \"content\" отличается от ожидаемого");
        softAssert.assertEquals(postWPDB.getStatus(), status,
                "Параметр поста в БД \"status\" отличается от ожидаемого");

        PostWP postWPApi = apiWpHelper.getPostWP(postId);
        softAssert.assertEquals(postWPApi.getId(), postId,
                "Параметр поста из ответа API \"ID\" отличается от ожидаемого");
        softAssert.assertEquals(postWPApi.getTitle(), title,
                "Параметр поста из ответа API \"title\" отличается от ожидаемого");
        softAssert.assertEquals(postWPApi.getContent(), content,
                "Параметр поста из ответа API \"content\" отличается от ожидаемого");
        softAssert.assertEquals(postWPApi.getStatus(), status,
                "Параметр поста из ответа API \"status\" отличается от ожидаемого");

        dbWpHelper.deletePostWP(postId);
        softAssert.assertNotEquals(postId, dbWpHelper.getLastPostWP().getId(),
                "ID удаленного поста не должен совпадать с последним ID в БД");
        softAssert.assertAll();
    }
}
