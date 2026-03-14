package worldPressTests.comments;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.worldPressPojo.CommentWP;
import org.example.worldPressPojo.PostWP;
import org.example.worldPressPojo.StatusPostWP;
import org.testng.annotations.Test;
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
        PostWP postWP = createPostWP(title, postContent, status);

        Integer post = postWP.getId();
        String commentContent = "Старый комментарий 2";
        CommentWP commentWP = createCommentWP(post, commentContent);

        String commentNewContent = "Новый комментарий 2";
        CommentWP updatedCommentWP = updateCommentWP(commentWP.getId(),commentNewContent);

        assertEquals(updatedCommentWP.getContent(), commentNewContent,
                "Обновленный параметр комментария \"content\" отличается от ожидаемого");
        assertEquals(updatedCommentWP.getPost(), postWP.getId(),
                "Параметр комментария \"Post\" и параметр поста \"ID\" не совпадают");

        CommentWP commentWPFromDB = getAllCommentsWPFromDB().get(commentWP.getId() - 1);
        assertEquals(commentWPFromDB.getId(), updatedCommentWP.getId(),
                "Параметр комментария из БД \"ID\" отличается от ожидаемого");
        assertEquals(commentWPFromDB.getPost(), updatedCommentWP.getPost(),
                "Параметр комментария из БД \"Post\" отличается от ожидаемого");
        assertEquals(commentWPFromDB.getContent(), updatedCommentWP.getContent(),
                "Параметр комментария из БД \"content\" отличается от ожидаемого");
        assertEquals(commentWPFromDB.getPost(), postWP.getId(),
                "Параметр комментария из БД \"Post\" и параметр поста из БД \"ID\" не совпадают");
    }
}
