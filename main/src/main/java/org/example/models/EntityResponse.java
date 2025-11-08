package org.example.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class EntityResponse {

    private AdditionRequest addition;
    private Integer id;
    @SerializedName("important_numbers")
    private List<Integer> importantNumbers;
    private String title;
    private Boolean verified;

    public EntityResponse() {
    }

    public EntityResponse(AdditionRequest addition,Integer id, List<Integer> importantNumbers, String title, Boolean verified) {
        this.addition = addition;
        this.id = id;
        this.importantNumbers = importantNumbers;
        this.title = title;
        this.verified = verified;
    }

    public AdditionRequest getAddition() {
        return addition;
    }

    public void setAddition(AdditionRequest addition) {
        this.addition = addition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getImportantNumbers() {
        return importantNumbers;
    }

    public void setImportantNumbers(List<Integer> importantNumbers) {
        this.importantNumbers = importantNumbers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityResponse that)) return false;
        return Objects.equals(addition, that.addition)
                && Objects.equals(importantNumbers, that.importantNumbers)
                && Objects.equals(title, that.title)
                && Objects.equals(verified, that.verified)
                && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addition, importantNumbers, title, verified, id);
    }

    @Override
    public String toString() {
        return "\nEntityResponse{" +
                "addition=" + addition +
                ", importantNumbers=" + importantNumbers +
                ", title='" + title + '\'' +
                ", verified=" + verified +
                ", id=" + id +
                '}';
    }
}
