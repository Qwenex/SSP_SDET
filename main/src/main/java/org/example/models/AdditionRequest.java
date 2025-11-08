package org.example.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AdditionRequest {

    @SerializedName("additional_info")
    private String additionalInfo;
    @SerializedName("additional_number")
    private Integer additionalNumber;

    public AdditionRequest() {
    }

    public AdditionRequest(String additionalInfo, Integer additionalNumber) {
        this.additionalInfo = additionalInfo;
        this.additionalNumber = additionalNumber;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Integer getAdditionalNumber() {
        return additionalNumber;
    }

    public void setAdditionalNumber(Integer additionalNumber) {
        this.additionalNumber = additionalNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdditionRequest that)) return false;
        return Objects.equals(additionalInfo, that.additionalInfo) && Objects.equals(additionalNumber, that.additionalNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(additionalInfo, additionalNumber);
    }

    @Override
    public String toString() {
        return "AdditionRequest{" +
                "additionalInfo='" + additionalInfo +
                ", additionalNumber=" + additionalNumber +
                '}';
    }
}
