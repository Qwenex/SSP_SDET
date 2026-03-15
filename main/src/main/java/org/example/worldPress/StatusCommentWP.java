package org.example.worldPress;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusCommentWP {
    APPROVED("approved"),
    TRASH("trash");

    private final String statusString;

    StatusCommentWP(String statusString) {
        this.statusString = statusString;
    }

    @JsonValue
    public String getStatusString() {
        return statusString;
    }

    @JsonCreator
    public static StatusCommentWP fromString(String value) {
        for (StatusCommentWP status : values()) {
            if (status.statusString.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Неизвестный статус: " + value);
    }

    @Override
    public String toString() {
        return statusString;
    }
}
