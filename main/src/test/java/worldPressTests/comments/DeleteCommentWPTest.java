package worldPressTests.comments;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.worldPress.CommentWP;
import org.example.worldPress.PostWP;
import org.example.worldPress.StatusCommentWP;
import org.example.worldPress.StatusPostWP;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import worldPressTests.BaseWPTest;

import static org.testng.Assert.assertEquals;

@Story("Удаление комментария")
public class DeleteCommentWPTest extends BaseWPTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Создание нового комментария на опубликованный пост с валидными данными")
    public void createCommentTest() {
        String title = "Пост 3";
        String postContent = "Описание 3";
        StatusPostWP status = StatusPostWP.PUBLISH;
        PostWP postWP = apiWpHelper.createPostWP(title, postContent, status);

        Integer post = postWP.getId();
        String commentContent = "Комментарий 1";
        CommentWP commentWP = apiWpHelper.createCommentWP(post, commentContent);
        CommentWP deletedCommentWP = apiWpHelper.deleteCommentWP(commentWP.getId());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(deletedCommentWP.getStatus(), StatusCommentWP.TRASH,
                "Параметр комментария \"status\" отличается от ожидаемого");

        CommentWP commentWPFromDB = dbWpHelper.getCommentWP(commentWP.getId());
        softAssert.assertEquals(commentWPFromDB.getStatus(), StatusCommentWP.TRASH,
                "Параметр комментария из БД \"status\" отличается от ожидаемого");
        softAssert.assertAll();

        dbWpHelper.deleteCommentWP(commentWP.getId());
        dbWpHelper.deletePostWP(postWP.getId());
    }
}
