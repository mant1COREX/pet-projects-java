package ru.ruslan.springmvc.models;

import jakarta.validation.constraints.*;

public class Person {

    private int personId;

    @NotEmpty(message = "ФИО не должно быть пустым")
    @Size(min = 10, max = 100, message = "ФИО должно содержать от 2 до 100 символов")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "ФИО должны быть в таком формате: Иванов Иван Иванович")
    private String name;

    @Min(value = 1900, message = "Год рождения должен быть не меньше 1900")
    @Max(value = 2025, message = "Год рождения не может быть больше текущего")
    private int born;

    public Person(int personId, String name, int born) {
        this.personId = personId;
        this.name = name;
        this.born = born;
    }

    public Person() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBorn() {
        return born;
    }

    public void setBorn(int born) {
        this.born = born;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
