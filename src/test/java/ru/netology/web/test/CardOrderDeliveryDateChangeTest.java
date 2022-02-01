package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

@Feature("Тестируем функциональность доставки карты")
public class CardOrderDeliveryDateChangeTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
//        Configuration.headless = true;
        open("http://0.0.0.0:9999");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Story("Позитивные кейсы: доставка карты оформлена успешно")
    @DisplayName("Проверяем перепланирование встречи с курьером на другую дату")
    @Test
    public void shouldCardDeliveryOrder() {
        val user = DataGenerator.Registration.generateInfo("ru");
        $("[data-test-id=city] input").val(user.getCity());
        $("[data-test-id=date] input").val(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").val(user.getPhone());
        $("[data-test-id=agreement]").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id=success-notification]")
                .shouldHave(text("Успешно! Встреча успешно запланирована на " + DataGenerator.generateDate(3)), Duration.ofSeconds(15));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.generateDate(5));
        $x("//*[text()='Запланировать']").doubleClick();
        $(withText("У вас уже запланирована встреча на другую дату")).shouldHave(text("Перепланировать?"), Duration.ofSeconds(20));
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification]")
                .shouldHave(text("Успешно! Встреча успешно запланирована на " + DataGenerator.generateDate(5)), Duration.ofSeconds(15));

    }

    @Story("Негативные кейсы: доставка карты не оформлена")
    @DisplayName("Проверяем отправку формы заказа карты с невалидным именем")
    @Test
    public void shouldCardDeliveryOrderInvalidName() {
        $("[data-test-id=city] input").val(DataGenerator.generateCity());
        $("[data-test-id=date] input").val(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(DataGenerator.generateInvalidName());
        $("[data-test-id=phone] input").val(DataGenerator.generatePhone());
        $("[data-test-id=agreement]").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id = 'name'].input_invalid .input__sub")
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Story("Негативные кейсы: доставка карты не оформлена")
    @DisplayName("Проверяем отправку формы заказа карты с недопустимой датой встречи")
    @Test
    public void shouldCardDeliveryOrderInvalidDate() {
        $("[data-test-id=city] input").val(DataGenerator.generateCity());
        $("[data-test-id=date] input").val(DataGenerator.generateDate(1));
        $("[data-test-id=name] input").setValue(DataGenerator.generateName());
        $("[data-test-id=phone] input").val(DataGenerator.generatePhone());
        $("[data-test-id=agreement]").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id=date] .input_invalid .input__sub")
                .shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Story("Негативные кейсы: доставка карты не оформлена")
    @DisplayName("Проверяем отправку формы заказа карты: поле город не заполнено")
    @Test
    public void shouldCardDeliveryOrderEmptyCity() {
        $("[data-test-id=date] input").val(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(DataGenerator.generateName());
        $("[data-test-id=phone] input").val(DataGenerator.generatePhone());
        $("[data-test-id=agreement]").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id = 'city'].input_invalid").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Story("Негативные кейсы: доставка карты не оформлена")
    @DisplayName("Проверяем отправку формы заказа карты: поле имя не заполнено")
    @Test
    public void shouldCardDeliveryOrderEmptyName() {
        $("[data-test-id=city] input").val(DataGenerator.generateCity());
        $("[data-test-id=date] input").val(DataGenerator.generateDate(3));
        $("[data-test-id=phone] input").val(DataGenerator.generatePhone());
        $("[data-test-id=agreement]").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id = 'name'].input_invalid").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Story("Негативные кейсы: доставка карты не оформлена")
    @DisplayName("Проверяем отправку формы заказа карты: поле телефон не заполнено")
    @Test
    public void shouldCardDeliveryOrderEmptyPhone() {
        $("[data-test-id=city] input").val(DataGenerator.generateCity());
        $("[data-test-id=date] input").val(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(DataGenerator.generateName());
        $("[data-test-id=agreement]").click();
        $x("//*[text()='Запланировать']").click();
        $("[data-test-id = 'phone'].input_invalid").shouldHave(text("Поле обязательно для заполнения"));
    }

}
