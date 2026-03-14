package org.example.worldPressPojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentWP {

    private Integer id;
    private Integer post;
    private String content;
    private StatusCommentWP status;

    public CommentWP() {
        this.status = StatusCommentWP.APPROVED;
    }

    public CommentWP(Integer post, String content) {
        this.post = post;
        this.content = content;
        this.status = StatusCommentWP.APPROVED;
    }

    public CommentWP(Integer id, Integer post, String content, StatusCommentWP status) {
        this.id = id;
        this.post = post;
        this.content = content;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public StatusCommentWP getStatus() {
        return status;
    }

    public void setStatus(StatusCommentWP status) {
        this.status = status;
    }

    @JsonProperty("content")
    public void setContent(Map<String, String> contentMap) {
        this.content = contentMap.get("raw");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CommentWP commentWP)) return false;
        return Objects.equals(id, commentWP.id)
                && Objects.equals(post, commentWP.post)
                && Objects.equals(content, commentWP.content)
                && status == commentWP.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, post, content, status);
    }

    @Override
    public String toString() {
        return String.format("\nID %s {\n post= %s,\n content= %s,\n status= %s\n}",
                id, post, content, status);
    }
}
