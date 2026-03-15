package org.example.worldPress;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusPostWP {
    PUBLISH("publish"),
    FUTURE("future"),
    DRAFT("draft"),
    PENDING("pending"),
    PRIVATE("private"),
    TRASH("trash"),
    INHERIT("inherit");

    private final String statusString;

    StatusPostWP(String statusString) {
        this.statusString = statusString;
    }

    @JsonValue
    public String getStatusString() {
        return statusString;
    }

    @JsonCreator
    public static StatusPostWP fromString(String value) {
        for (StatusPostWP status : values()) {
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
