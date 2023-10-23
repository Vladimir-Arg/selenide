package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class cardDeliveryChooseTest {
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
        $("[data-test-id=city] input").setValue("мо"); // Ввод первых 2 символов города
        $$(".menu-item__control").findBy(text("Москва")).click();
        String planingDate = generateDate(10, "dd.MM.yyyy"); // расчет планируемой даты
        String planingDay = generateDate(10, "d");

        $("[data-test-id=date] input").click();

            if (!generateDate(3, "MM").equals(generateDate(10, "MM"))) {
                $("[data-step='1']").click();
              }
            $$("td.calendar__day").findBy(Condition.text(planingDay)).click();
            $("[data-test-id=name] input").setValue("Иванов");
            $("[data-test-id=phone] input").setValue("+79270000000");
            $("[data-test-id=agreement]").click();
            $("button.button").click();
            $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                    .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planingDate));
    }
}
