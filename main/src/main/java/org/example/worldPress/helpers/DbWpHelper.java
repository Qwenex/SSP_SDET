package org.example.worldPress.helpers;

import io.qameta.allure.Step;
import org.example.utils.ConnectMySqlDB;
import org.example.worldPress.CommentWP;
import org.example.worldPress.PostWP;
import org.example.worldPress.StatusCommentWP;
import org.example.worldPress.StatusPostWP;

import java.util.List;

public class DbWpHelper {

    private final static String SQL_POSTS_REQUEST =
            "SELECT ID, post_title, post_content, post_status FROM wp_posts ";
    private final static String SQL_COMMENTS_REQUEST =
            "SELECT comment_id, comment_post_ID, comment_content, comment_approved FROM wp_comments ";

    @Step("Получение постов из базы данных")
    public List<PostWP> getAllPostsWPFromDB() {
        return ConnectMySqlDB.getData(
                SQL_POSTS_REQUEST + "ORDER BY ID",
                rs -> new PostWP(
                        rs.getInt("ID"),
                        rs.getString("post_title"),
                        rs.getString("post_content"),
                        StatusPostWP.fromString(rs.getString("post_status"))
                )
        );
    }

    @Step("Получение комментариев из базы данных")
    public List<CommentWP> getAllCommentsWPFromDB() {
        return ConnectMySqlDB.getData(
                SQL_COMMENTS_REQUEST + "ORDER BY comment_id",
                rs -> {
                    String dbStatus = rs.getString("comment_approved");
                    StatusCommentWP status = "1".equals(dbStatus) ?
                            StatusCommentWP.APPROVED : StatusCommentWP.fromString(dbStatus);
                    return new CommentWP(
                            rs.getInt("comment_id"),
                            rs.getInt("comment_post_ID"),
                            rs.getString("comment_content"),
                            status
                    );
                }
        );
    }
}
