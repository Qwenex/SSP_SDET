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

@Story("Удаление поста")
public class DeletePostWPTest extends BaseWPTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Удаление поста")
    public void createPostTest() {
        String title = "Пост на удаление";
        String content = "Описание поста на удаление";
        StatusPostWP status = StatusPostWP.PENDING;
        PostWP postWP = apiWpHelper.createPostWP(title, content, status);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(postWP.getStatus(), status,
                "Параметр поста \"status\" отличается от ожидаемого");

        PostWP deletedPostWP = apiWpHelper.deletePostWP(postWP.getId());
        softAssert.assertEquals(deletedPostWP.getStatus(), StatusPostWP.TRASH,
                "Параметр поста \"status\" отличается от ожидаемого");

        PostWP postWPFromDB = dbWpHelper.getPostWP(postWP.getId());
        softAssert.assertEquals(postWPFromDB.getId(), deletedPostWP.getId(),
                "Параметр поста из БД \"ID\" отличается от ожидаемого");
        softAssert.assertEquals(postWPFromDB.getStatus(), StatusPostWP.TRASH,
                "Параметр поста из БД \"status\" отличается от ожидаемого");
        softAssert.assertAll();

        dbWpHelper.deletePostWP(postWP.getId());
        dbWpHelper.deletePostWP(postWP.getId() + 1);
    }
}
