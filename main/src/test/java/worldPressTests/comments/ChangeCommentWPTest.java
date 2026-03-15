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

import static org.testng.Assert.assertEquals;

@Story("Изменение комментария к посту")
public class ChangeCommentWPTest  extends BaseWPTest {

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Изменение комментария на опубликованный пост")
    public void updateCommentTest() {
        String title = "Пост 2";
        String postContent = "Описание 2";
        StatusPostWP status = StatusPostWP.PUBLISH;
        PostWP postWP =  apiWpHelper.createPostWP(title, postContent, status);

        Integer post = postWP.getId();
        String commentContent = "Старый комментарий 2";
        CommentWP commentWP = apiWpHelper.createCommentWP(post, commentContent);

        String commentNewContent = "Новый комментарий 2";
        CommentWP updatedCommentWP = apiWpHelper.updateCommentWP(commentWP.getId(),commentNewContent);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updatedCommentWP.getContent(), commentNewContent,
                "Обновленный параметр комментария \"content\" отличается от ожидаемого");
        softAssert.assertEquals(updatedCommentWP.getPost(), postWP.getId(),
                "Параметр комментария \"Post\" и параметр поста \"ID\" не совпадают");

        CommentWP commentWPFromDB = dbWpHelper.getAllCommentsWPFromDB().get(commentWP.getId() - 1);
        softAssert.assertEquals(commentWPFromDB.getId(), updatedCommentWP.getId(),
                "Параметр комментария из БД \"ID\" отличается от ожидаемого");
        softAssert.assertEquals(commentWPFromDB.getPost(), updatedCommentWP.getPost(),
                "Параметр комментария из БД \"Post\" отличается от ожидаемого");
        softAssert.assertEquals(commentWPFromDB.getContent(), updatedCommentWP.getContent(),
                "Параметр комментария из БД \"content\" отличается от ожидаемого");
        softAssert.assertEquals(commentWPFromDB.getPost(), postWP.getId(),
                "Параметр комментария из БД \"Post\" и параметр поста из БД \"ID\" не совпадают");
        softAssert.assertAll();
    }
}
