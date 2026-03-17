package worldPressTests.posts;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.worldPress.PostWP;
import org.example.worldPress.StatusPostWP;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import worldPressTests.BaseWPTest;

import static org.testng.Assert.assertEquals;

@Story("Изменение поста")
public class ChangePostWPTest extends BaseWPTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Изменение поста ")
    public void updatePostTest() {
        String title = "Старое название поста";
        String content = "Старое описание поста";
        StatusPostWP status = StatusPostWP.PENDING;
        PostWP postWP = apiWpHelper.createPostWP(title, content, status);

        Integer originPostID = postWP.getId();
        String newTitle = "Новое название поста";
        String newContent = "Новое описание поста";
        StatusPostWP newStatus = StatusPostWP.PUBLISH;
        PostWP updatedPostWP = apiWpHelper.updatePostWP(originPostID, newTitle, newContent, newStatus);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updatedPostWP.getTitle(), newTitle,
                "Новый параметр поста \"title\" отличается от ожидаемого");
        softAssert.assertEquals(updatedPostWP.getContent(), newContent,
                "Новый параметр поста \"content\" отличается от ожидаемого");
        softAssert.assertEquals(updatedPostWP.getStatus(), newStatus,
                "Новый параметр поста \"status\" отличается от ожидаемого");

        PostWP postWPFromDB = dbWpHelper.getPostWP(postWP.getId());
        softAssert.assertEquals(postWPFromDB.getId(), originPostID,
                "Параметр поста из БД \"ID\" отличается от ожидаемого");
        softAssert.assertEquals(postWPFromDB.getTitle(), newTitle,
                "Параметр поста из БД \"title\" отличается от ожидаемого");
        softAssert.assertEquals(postWPFromDB.getContent(), newContent,
                "Параметр поста из БД \"content\" отличается от ожидаемого");
        softAssert.assertEquals(postWPFromDB.getStatus(), newStatus,
                "Параметр поста из БД \"status\" отличается от ожидаемого");
        softAssert.assertAll();

        dbWpHelper.deletePostWP(originPostID);
        dbWpHelper.deletePostWP(originPostID + 1);
    }
}
