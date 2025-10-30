package org.example.utilite;

import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

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

    /**
     * Генерация рандомного PostCode
     * @return Post Code состоящий из 10 цифр
     */
    private String generatePostCode() {
        Random rnd = new Random();
        return rnd.ints(10, 0, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }

    /**
     * Генерация рандомного имени на основе Post Code,
     * преобразующее каждую пару цифр в соответсвующую букву латинского алфавита
     * Прим. 0001020304 -> abcde
     * @param postCode Post Code
     * @return Имя состоящее из a-z символов
     */
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

    /**
     * Генерация рандомной фамилии на основе Post Code,
     * преобразующее каждую пару цифр в соответсвующую букву латинского алфавита в обратном порядке
     * Прим. 0001020304 -> zyxwv
     * @param postCode Post Code
     * @return Фамилия состоящяя из a-z символов
     */
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
