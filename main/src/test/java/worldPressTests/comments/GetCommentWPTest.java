package worldPressTests.comments;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.worldPress.CommentWP;
import org.example.worldPress.PostWP;
import org.example.worldPress.StatusPostWP;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import worldPressTests.BaseWPTest;

@Story("Получение комментария")
public class GetCommentWPTest extends BaseWPTest {

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Получение комментария через Api созданного через БД")
    public void getCommentTest() {
        String title = "Пост из бд 2";
        String content = "Описание поста из бд 2";
        StatusPostWP status = StatusPostWP.PENDING;
        PostWP postWPDB = dbWpHelper.setPostWP(title, content, status);
        Integer postId = postWPDB.getId();

        String commentContent = "Комментарий созданный через бд";
        CommentWP commentWPBD = dbWpHelper.setCommentWP(postId, commentContent);
        Integer commentId = commentWPBD.getId();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(commentWPBD.getPost(), postId,
                "Параметр комментария в БД \"postId\" отличается от ожидаемого");
        softAssert.assertEquals(commentWPBD.getContent(), commentContent,
                "Параметр комментария в БД \"content\" отличается от ожидаемого");

        CommentWP commentWPApi = apiWpHelper.getCommentWP(commentId);
        softAssert.assertEquals(commentWPApi.getPost(), postId,
                "Параметр комментария из ответа API \"postId\" отличается от ожидаемого");
        softAssert.assertEquals(commentWPApi.getContent(), commentContent,
                "Параметр комментария из ответа API \"content\" отличается от ожидаемого");
        System.out.println(apiWpHelper.getCommentWP(commentId).getContent());

        dbWpHelper.deleteCommentWP(commentId);
        softAssert.assertNotEquals(commentId, dbWpHelper.getLastCommentWP().getId(),
                "ID удаленного комментария не должен совпадать с последним ID в БД");

        dbWpHelper.deletePostWP(postId);
        softAssert.assertNotEquals(postId, dbWpHelper.getLastPostWP().getId(),
                "ID удаленного поста не должен совпадать с последним ID в БД");
        softAssert.assertAll();
    }
}
