package ru.netology.web.data;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


public class DataGenerator {
    private DataGenerator() {
    }

    static Faker faker = new Faker(new Locale("ru"));


    public static class Registration {
        @Step("Генерируем валидные данные для заполнения формы заказа карты: город, ФИО, телефон")
        public static RegistrationInfo generateInfo(String locale) {
            return new RegistrationInfo(generateCity(),
                    generateName(),
                    generatePhone());
        }
    }

    @Step("Генерируем город для доставки карты")
    public static String generateCity() {
        String[] city = new String[]
                {"Москва", "Кемерово", "Майкоп", "Симферополь", "Тамбов", "Мурманск", "Владимир", "Самара", "Магадан"};
        int index = new Random().nextInt(city.length);
        return city[index];
    }

    @Step("Генерируем дату для доставки карты")
    public static String generateDate(int plusDays) {
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Step("Генерируем Ф.И.О. для заказа карты")
    public static String generateName() {
        return faker.name().fullName();
    }

    @Step("Генерируем телефон для связи с курьером")
    public static String generatePhone() {
        return faker.phoneNumber().phoneNumber();
    }

    @Step("Генерируем недопустимое имя для проверки метода")
    public static String generateInvalidName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }

}

