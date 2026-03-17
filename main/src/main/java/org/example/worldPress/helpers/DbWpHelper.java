package org.example.worldPress.helpers;

import io.qameta.allure.Step;
import org.example.utils.ConnectMySqlDB;
import org.example.utils.ResultSetMapper;
import org.example.worldPress.CommentWP;
import org.example.worldPress.PostWP;
import org.example.worldPress.StatusCommentWP;
import org.example.worldPress.StatusPostWP;

import java.time.*;
import java.util.List;

public class DbWpHelper {

    private final static String SQL_SELECT_POSTS_REQUEST =
            "SELECT ID, post_title, post_content, post_status FROM wp_posts ";

    private final static String SQL_SELECT_COMMENTS_REQUEST =
            "SELECT comment_id, comment_post_ID, comment_content, comment_approved FROM wp_comments ";

    private final static String SQL_INSERT_POSTS_REQUEST = """
            INSERT INTO wp_posts (post_title, post_content, post_status, post_date, post_date_gmt,
             post_modified, post_modified_gmt, post_excerpt, to_ping, pinged, post_content_filtered)
            VALUES ('%s','%s','%s','%s', '%s', '%s', '%s', '', '', '', '');""";

    private final static String SQL_INSERT_COMMENTS_REQUEST = """
            INSERT INTO wp_comments (comment_post_ID, comment_content, comment_approved,
             comment_date, comment_date_gmt, comment_author)
            VALUES ('%s','%s','1','%s', '%s', '');""";

    private final static String SQL_DELETE_POSTS_REQUEST =
            "DELETE FROM wp_posts WHERE ID = %s;";

    private final static String SQL_DELETE_COMMENTS_REQUEST =
            "DELETE FROM wp_comments WHERE comment_id = %s;";

    private static ResultSetMapper<PostWP> defaultPostsWPMapper() {
        return rs -> new PostWP(
                rs.getInt("ID"),
                rs.getString("post_title"),
                rs.getString("post_content"),
                StatusPostWP.fromString(rs.getString("post_status"))
        );
    }

    private static ResultSetMapper<CommentWP> defaultCommentsWPMapper() {
        return rs -> {
            String dbStatus = rs.getString("comment_approved");
            StatusCommentWP status = "1".equals(dbStatus) ?
                    StatusCommentWP.APPROVED : StatusCommentWP.fromString(dbStatus);
            return new CommentWP(
                    rs.getInt("comment_id"),
                    rs.getInt("comment_post_ID"),
                    rs.getString("comment_content"),
                    status
            );
        };
    }

    @Step("Получение всех постов из базы данных")
    public List<PostWP> getAllPostsWP() {
        return ConnectMySqlDB.getData(
                SQL_SELECT_POSTS_REQUEST + "ORDER BY ID",
                defaultPostsWPMapper()
        );
    }

    @Step("Получение поста из базы данных")
    public PostWP getPostWP(Integer postId) {
        return ConnectMySqlDB.getData(
                SQL_SELECT_POSTS_REQUEST + "WHERE ID =" + postId,
                defaultPostsWPMapper()
        ).get(0);
    }

    @Step("Получение всех комментариев из базы данных")
    public List<CommentWP> getAllCommentsWP() {
        return ConnectMySqlDB.getData(
                SQL_SELECT_COMMENTS_REQUEST + "ORDER BY comment_id",
                defaultCommentsWPMapper()
        );
    }

    @Step("Получение комментария из базы данных")
    public CommentWP getCommentWP(Integer commentId) {
        return ConnectMySqlDB.getData(
                SQL_SELECT_COMMENTS_REQUEST + "WHERE comment_id =" + commentId,
                defaultCommentsWPMapper()
        ).get(0);
    }

    @Step("Получение последнего поста в базе данных")
    public PostWP getLastPostWP() {
        List<PostWP> postWPList = getAllPostsWP();
        return postWPList.get(postWPList.size() - 1);
    }

    @Step("Получение последнего комментария в базе данных")
    public CommentWP getLastCommentWP() {
        List<CommentWP> commentWPList = getAllCommentsWP();
        return commentWPList.get(commentWPList.size() - 1);
    }

    @Step("Создание нового поста в базе данных")
    public PostWP setPostWP(String title, String content, StatusPostWP status) {
        String nowDateTime = LocalDateTime.now().toString();
        String nowGmt = LocalDateTime.now(ZoneId.of("UTC")).toString();
        ConnectMySqlDB.setData(String.format(SQL_INSERT_POSTS_REQUEST,
                title, content, status.toString(), nowDateTime, nowGmt, nowDateTime, nowGmt));
        return getLastPostWP();
    }

    @Step("Создание нового комментария в базе данных")
    public CommentWP setCommentWP(Integer postId, String content) {
        String nowDateTime = LocalDateTime.now().toString();
        String nowGmt = LocalDateTime.now(ZoneId.of("UTC")).toString();
        ConnectMySqlDB.setData(String.format(SQL_INSERT_COMMENTS_REQUEST,
                postId, content, nowDateTime, nowGmt));
        return getLastCommentWP();
    }

    @Step("Удаление поста из базы данных")
    public void deletePostWP(Integer postId) {
        ConnectMySqlDB.setData(String.format(SQL_DELETE_POSTS_REQUEST, postId));
    }

    @Step("Удаление комментария из базы данных")
    public void deleteCommentWP(Integer commentId) {
        ConnectMySqlDB.setData(String.format(SQL_DELETE_COMMENTS_REQUEST, commentId));
    }
}
