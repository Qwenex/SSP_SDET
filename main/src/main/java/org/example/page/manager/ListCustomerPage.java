package org.example.page.manager;

import io.qameta.allure.Step;
import org.example.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;

public class ListCustomerPage extends BasePage {

    private final static String URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager/list";

    @FindBy(css = "tbody td:nth-child(1)")
    private List<WebElement> firstNames;

    @FindBy(css = "table tr td:nth-child(1) a")
    private WebElement sortByNameButton;

    private static final String deleteCustomerButton =
            "//tbody//td[text()='%s']/..//button[text()='Delete']";

    public ListCustomerPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открытие страницы")
    public ListCustomerPage openPage() {
        webDriver.get(URL);
        return this;
    }

    @Step("Скролл страницы до элемента")
    private void scrollToElement(WebElement webElement) {
        Actions actions = new Actions(webDriver);
        actions.moveToElement(webElement);
        actions.perform();
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    @Step("Получение списка имен")
    public ArrayList<String> getNames() {
        wait.until(ExpectedConditions.visibilityOfAllElements(firstNames));
        ArrayList<String> names = new ArrayList<>();

        if (firstNames == null || firstNames.isEmpty()) {
            return names;
        }
        for (WebElement firstName : firstNames) {
            names.add(firstName.getText());
        }
        return names;
    }

    @Step("Получение отсортированного списка имен в алфовитном порядке")
    public ArrayList<String> getSortedNamesByNaturalOrderList() {
        sortByNameButton.click();
        sortByNameButton.click();
        return getNames();
    }

    @Step("Получение имени, длина которого ближе всего к среднему арифметичскому")
    public String nameWithAverageLength() {
        List<String> firstNames = getNames();
        double averageLength = firstNames.stream().mapToInt(String::length).average().orElse(0.0);
        return firstNames.stream().min(Comparator.comparingDouble(name -> Math.abs(name.length() - averageLength))).get();
    }

    /**
     * Удаление клиента по имени, фамилии или post code
     * @param value Имя или фамилии или post code клиента
     */
    @Step("Удаление клиента")
    public void deleteCustomer(String value) {
        String xpath = String.format(deleteCustomerButton, value);
        try {
            WebElement deleteButton = webDriver.findElement(By.xpath(xpath));
            scrollToElement(deleteButton);
            deleteButton.click();
        } catch (NoSuchElementException e) {
            System.out.printf("Кнопка удаления у \"%s\" в списке не найдена. %s", value, e);
        }
    }
}
