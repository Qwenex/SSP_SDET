package org.example.classes;

import io.qameta.allure.Step;

public class Calc {

    @Step(value = "Сложение")
    public static Double plus(Double a, Double b) {
        return a + b;
    }

    @Step(value = "Вычетание")
    public static Double minus(Double a, Double b) {
        return (double) Math.round((a - b) * 1000) / 1000;
    }

    @Step(value = "Умножение")
    public static Double multiplication(Double a, Double b) {
        return a * b;
    }

    @Step(value = "Деление")
    public static Double division(Double a, Double b) {
        if (b == 0.0) {
            throw new ArithmeticException("Деление на ноль не поддерживается");
        }
        return (double) Math.round(a / b * 1000) / 1000;
    }

    @Step(value = "Возведение в степень")
    public static Double sqr(Double a) {
        return a * a;
    }
}