package ru.netology.web.data;

import lombok.Data;

@Data
public class RegistrationInfo {
    private final String city;
    private final String data;
    private final String name;
    private final String phone;
}
