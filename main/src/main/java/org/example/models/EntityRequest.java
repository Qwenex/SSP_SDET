package org.example.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class EntityRequest {

    private AdditionRequest addition;
    @SerializedName("important_numbers")
    private List<Integer> importantNumbers;
    private String title;
    private Boolean verified;

    public EntityRequest() {
    }

    public EntityRequest(AdditionRequest addition, List<Integer> importantNumbers, String title, Boolean verified) {
        this.addition = addition;
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
        if (!(o instanceof EntityRequest that)) return false;
        return Objects.equals(addition, that.addition)
                && Objects.equals(importantNumbers, that.importantNumbers)
                && Objects.equals(title, that.title)
                && Objects.equals(verified, that.verified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addition, importantNumbers, title, verified);
    }

    @Override
    public String toString() {
        return "EntityRequest{" +
                "addition=" + addition +
                ", importantNumbers=" + importantNumbers +
                ", title='" + title + '\'' +
                ", verified=" + verified +
                '}';
    }
}
