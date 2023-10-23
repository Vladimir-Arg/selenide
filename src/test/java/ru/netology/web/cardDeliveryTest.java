package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class cardDeliveryTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
/**
    Город — один из административных центров субъектов РФ.
    Дата — не ранее трёх дней с текущей даты.
    В поле фамилии и имени разрешены только русские буквы, дефисы и пробелы.
    В поле телефона — только 11 цифр, символ + на первом месте.
    Флажок согласия должен быть выставлен.
**/
    @Test
    void shouldTest1() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Архангельск");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Иванов");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }


}
