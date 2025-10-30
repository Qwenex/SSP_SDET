package org.example.utilite;

import java.util.Objects;
import java.util.Random;

public class GenerationCustomer {

    private final String firstName;
    private final String lastName;
    private final String postCode;

    public GenerationCustomer() {
        this.postCode = generatePostCode();
        this.firstName = generateFirstName(postCode);
        this.lastName = generateLastName(postCode);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPostCode() {
        return postCode;
    }

    private String generatePostCode() {
        int postCodeLength = 10;
        Random rnd = new Random();
        StringBuilder postCode = new StringBuilder();

        for (int i = 0; i < postCodeLength; i++) {
            postCode.append(rnd.nextInt(10));
        }

        return postCode.toString();
    }

    private String generateFirstName(String postCode) {
        StringBuilder firstName = new StringBuilder();

        for (int i = 0; i < (postCode.length() / 2); i++) {
            String doubleNumber = postCode.substring(i * 2, (i * 2) + 2);
            int number = Integer.parseInt(doubleNumber);
            char letter = (char) ('a' + (number % 26));
            firstName.append(letter);
        }

        return firstName.toString();
    }

    private String generateLastName(String postCode) {
        StringBuilder lastName = new StringBuilder();

        for (int i = 0; i < (postCode.length() / 2); i++) {
            String doubleNumber = postCode.substring(i * 2, (i * 2) + 2);
            int number = Integer.parseInt(doubleNumber);
            char letter = (char) ('z' - (number % 26));
            lastName.append(letter);
        }
        return lastName.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenerationCustomer that)) return false;
        return Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(postCode, that.postCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, postCode);
    }

    @Override
    public String toString() {
        return String.format("Name: %s, LastName: %s, PostCode: %s",
                firstName, lastName, postCode);
    }
}
