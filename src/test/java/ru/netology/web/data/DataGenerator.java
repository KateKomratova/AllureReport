package ru.netology.web.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


public class DataGenerator {
    private DataGenerator() {
    }

    static Faker faker = new Faker(new Locale("ru"));

    public static class Registration {
        public static RegistrationInfo generateInfo(String locale) {
            return new RegistrationInfo(generateCity(),
                    generateDate(3),
                    generateName(),
                    generatePhone());
        }
    }

    public static String generateCity() {
        String[] city = new String[]
                {"Москва", "Кемерово", "Майкоп", "Симферополь", "Тамбов", "Мурманск", "Владимир", "Самара", "Магадан"};
        int index = new Random().nextInt(city.length);
        return city[index];
    }

    public static String generateDate(int plusDays) {
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateName() {
        return faker.name().fullName();
    }

    public static String generatePhone() {
        return faker.phoneNumber().phoneNumber();
    }
}

