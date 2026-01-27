package org.example.classes;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class PersonPageSelenide {

    private SelenideElement birthDateLabel = $(By.xpath(
            "//*[text()='Дата\u00A0рождения']/..//span[@class='nowrap']"));
    private SelenideElement deathDateLabel = $(By.xpath(
            "//*[text()='Дата\u00A0смерти']/..//span[@class='nowrap']"));

    public String getBirthDate() {
        return birthDateLabel.getText();
    }

    public String getDeathDate() {
        return deathDateLabel.getText();
    }
}
