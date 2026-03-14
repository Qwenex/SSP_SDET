package worldPressTests.posts;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.worldPressPojo.PostWP;
import org.example.worldPressPojo.StatusPostWP;
import org.testng.annotations.Test;
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
        PostWP postWP = createPostWP(title, content, status);

        assertEquals(postWP.getStatus(), status,
                "Параметр поста \"status\" отличается от ожидаемого");

        PostWP deletedPostWP = deletePostWP(postWP.getId());
        assertEquals(deletedPostWP.getStatus(), StatusPostWP.TRASH,
                "Параметр поста \"status\" отличается от ожидаемого");

        PostWP postWPFromDB = getAllPostsWPFromDB().get(postWP.getId() - 1);
        assertEquals(postWPFromDB.getId(), deletedPostWP.getId(),
                "Параметр поста из БД \"ID\" отличается от ожидаемого");
        assertEquals(postWPFromDB.getStatus(), StatusPostWP.TRASH,
                "Параметр поста из БД \"status\" отличается от ожидаемого");
    }
}
