package worldPressTests.comments;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.worldPressPojo.CommentWP;
import org.example.worldPressPojo.PostWP;
import org.example.worldPressPojo.StatusCommentWP;
import org.example.worldPressPojo.StatusPostWP;
import org.testng.annotations.Test;
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
        PostWP postWP = createPostWP(title, postContent, status);

        Integer post = postWP.getId();
        String commentContent = "Комментарий 1";
        CommentWP commentWP = createCommentWP(post, commentContent);
        CommentWP deletedCommentWP = deleteCommentWP(commentWP.getId());
        assertEquals(deletedCommentWP.getStatus(), StatusCommentWP.TRASH,
                "Параметр комментария \"status\" отличается от ожидаемого");

        CommentWP commentWPFromDB = getAllCommentsWPFromDB().get(commentWP.getId() - 1);
        assertEquals(commentWPFromDB.getStatus(), StatusCommentWP.TRASH,
                "Параметр комментария из БД \"status\" отличается от ожидаемого");
    }
}
