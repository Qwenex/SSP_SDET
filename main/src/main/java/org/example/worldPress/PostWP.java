package org.example.worldPress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostWP {

    private Integer id;
    private String title;
    private String content;
    private StatusPostWP status;

    public PostWP() {
        this.status = StatusPostWP.DRAFT;
    }

    public PostWP(String title, String content) {
        this.title = title;
        this.content = content;
        this.status = StatusPostWP.DRAFT;
    }

    public PostWP(Integer id, String title, String content, StatusPostWP status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public StatusPostWP getStatus() {
        return status;
    }

    public void setStatus(StatusPostWP status) {
        this.status = status;
    }

    @JsonProperty("title")
    public void setTitle(Map<String, String> titleMap) {
        this.title = titleMap.get("rendered");
    }

    @JsonProperty("content")
    public void setContent(Map<String, String> contentMap) {
        this.content = contentMap.get("rendered").replaceAll("<[^>]*>", "").trim();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PostWP postWP)) return false;
        return Objects.equals(id, postWP.id)
                && Objects.equals(title, postWP.title)
                && Objects.equals(content, postWP.content)
                && status == postWP.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, status);
    }

    @Override
    public String toString() {
        return String.format("\nID %s {\n tittle= \"%s\",\n content= \"%s\",\n status= %s\n}",
                id, title, content, status);
    }
}
