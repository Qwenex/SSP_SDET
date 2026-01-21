import io.qameta.allure.*;
import org.example.Calc;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CalcTest {
    @DataProvider(name = "plusDataProvider")
    public static Object[][] plusDataProvider() {
        return new Object[][]{
                {2.0, 2.0, 4.0},
                {-2.0, 2.0, 0.0},
                {-2.0, -2.0, -4.0},
                {0.0, 0.0, 0.0},
                {0.3, 0.1, 0.4}
        };
    }

    @Epic(value = "Основные функции калькулятора")
    @Feature(value = "Простые операции калькулятора")
    @Story(value = "Сложение двух чисел")
    @Severity(SeverityLevel.CRITICAL)
    @Description(value = "Ввод двух чисел. На выходе должно быть число, являющееся суммой этих чисел")
    @Test(description = "Сложение", dataProvider = "plusDataProvider")
    public void plusTest(Double a, Double b, Double answer) {
        Double plusActual = Calc.plus(a, b);
        Assert.assertNotNull(plusActual, "Число не должно быть равно null");
        Assert.assertEquals(plusActual, answer, "Результат функции \"Сложение\" отличается от ожидаемого");
    }

    @DataProvider(name = "minusDataProvider")
    public static Object[][] minusDataProvider() {
        return new Object[][]{
                {2.0, 2.0, 0.0},
                {-2.0, 2.0, -4.0},
                {-2.0, -2.0, 0.0},
                {0.0, 0.0, 0.0},
                {0.3, 0.1, 0.2}
        };
    }

    @Epic(value = "Основные функции калькулятора")
    @Feature(value = "Простые операции калькулятора")
    @Story(value = "Вычетание двух чисел")
    @Severity(SeverityLevel.CRITICAL)
    @Description(value = "Ввод двух чисел. На выходе должно быть число, являющееся вычитанием второо числа из первого")
    @Test(description = "Вычетание", dataProvider = "minusDataProvider")
    public void minusTest(Double a, Double b, Double answer) {
        Double minusActual = Calc.minus(a, b);
        Assert.assertNotNull(minusActual, "Число не должно быть равно null");
        Assert.assertEquals(minusActual, answer, "Результат функции \"Вычетание\" отличается от ожидаемого");
    }

    @DataProvider(name = "multiplicationDataProvider")
    public static Object[][] multiplicationDataProvider() {
        return new Object[][]{
                {2.0, 2.0, 5.0},
                {-2.0, 2.0, -4.0},
                {-2.0, -2.0, 4.0},
                {0.0, 0.0, 0.0},
                {0.3, 0.1, 0.03}
        };
    }

    @Epic(value = "Основные функции калькулятора")
    @Feature(value = "Простые операции калькулятора")
    @Story(value = "Умножение чисел")
    @Severity(SeverityLevel.CRITICAL)
    @Description(value = "Ввод двух чисел. На выходе должно быть число, являющееся Умножение этих чисел")
    @Test(description = "Умножение", dataProvider = "multiplicationDataProvider")
    public void multiplicationTest(Double a, Double b, Double answer) {
        Double multiplicationActual = Calc.multiplication(a, b);
        Assert.assertNotNull(multiplicationActual, "Число не должно быть равно null");
        Assert.assertEquals(multiplicationActual, answer, "Результат функции \"Умножение\" отличается от ожидаемого");
    }

    @DataProvider(name = "divisionDataProvider")
    public static Object[][] divisionDataProvider() {
        return new Object[][]{
                {2.0, 2.0, 1.0},
                {-2.0, 2.0, -1.0},
                {-2.0, -2.0, 1.0},
                {0.3, 0.1, 3.0},
                {0.07, 23.42, 0.003}
        };
    }

    @Epic(value = "Основные функции калькулятора")
    @Feature(value = "Простые операции калькулятора")
    @Story(value = "Деление чисел")
    @Severity(SeverityLevel.CRITICAL)
    @Description(value = "Ввод двух чисел. На выходе должно быть число, являющееся делением первого числа на второе")
    @Test(description = "Деление", dataProvider = "divisionDataProvider")
    public void divisionTest(Double a, Double b, Double answer) {
        Double divisionActual = Calc.division(a, b);
        Assert.assertNotNull(divisionActual, "Число не должно быть равно null");
        Assert.assertEquals(divisionActual, answer, "Результат функции \"Деление\" отличается от ожидаемого");
    }

    @Epic(value = "Основные функции калькулятора")
    @Feature(value = "Простые операции калькулятора")
    @Story(value = "Деление числа на ноль")
    @Severity(SeverityLevel.NORMAL)
    @Description(value = "При делении на ноль должна выпадать ошибка RuntimeException")
    @Test(description = "Деление на ноль",
            expectedExceptions = ArithmeticException.class)
    public void divisionByZeroTest() {
        Calc.division(6.0, 0.0);
    }

    @DataProvider(name = "sqrDataProvider")
    public static Object[][] sqrDataProvider() {
        return new Object[][]{
                {2.0, 4.0},
                {-2.0, 4.0},
                {0.0, 0.0},
                {0.3, 0.09}
        };
    }

    @Epic(value = "Основные функции калькулятора")
    @Feature(value = "Простые операции калькулятора")
    @Story(value = "Возведение числа в квадрат")
    @Severity(SeverityLevel.CRITICAL)
    @Description(value = "Ввод одного числа. На выходе должно быть число, возведенное в квадрат ")
    @Test(description = "Возведение в квадрат", dataProvider = "sqrDataProvider")
    public void sqrTest(Double a, Double answer) {
        Double sqrActual = Calc.sqr(a);
        Assert.assertNotNull(sqrActual, "Число не должно быть равно null");
        Assert.assertEquals(sqrActual, answer, "Результат функции \"возведение в квадрат\" отличается от ожидаемого");
    }
}
