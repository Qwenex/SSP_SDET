package org.example.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AdditionResponse {

    @SerializedName("additional_info")
    private String additionalInfo;
    @SerializedName("additional_number")
    private Integer additionalNumber;
    private Integer id;

    public AdditionResponse() {
    }

    public AdditionResponse(String additionalInfo, Integer additionalNumber, Integer id) {
        this.additionalInfo = additionalInfo;
        this.additionalNumber = additionalNumber;
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdditionResponse that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(additionalInfo, that.additionalInfo) && Objects.equals(additionalNumber, that.additionalNumber) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), additionalInfo, additionalNumber, id);
    }

    @Override
    public String toString() {
        return "\nAdditionResponse{" +
                "additionalInfo='" + additionalInfo +
                ", additionalNumber=" + additionalNumber +
                ", id=" + id +
                '}';
    }
}
