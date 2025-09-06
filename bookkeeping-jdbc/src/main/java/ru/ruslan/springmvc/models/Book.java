package ru.ruslan.springmvc.models;

import jakarta.validation.constraints.*;

public class Book {

    private int bookId;
    private Integer personId;

    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов")
    @Pattern(regexp = "^[А-Яа-яЁёA-Za-z0-9 ,.!?\\-:;\"'()]{2,100}$", message = "Название книги может содержать буквы, цифры, пробелы и знаки пунктуации")
    private String name;

    @NotEmpty(message = "Имя автора не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно быть от 2 до 100 символов")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+(?:-[А-ЯЁ][а-яё]+)?( [А-ЯЁ][а-яё]+){1,2}$", message = "Имя автора должно быть в формате: Пушкин Александр Сергеевич")
    private String author;

    @Min(value = 1500, message = "Год должен быть не меньше 1500")
    @Max(value = 2025, message = "Год не может быть больше текущего")
    private int year;

    public Book(int bookId, Integer personId, String name, String author, int year) {
        this.bookId = bookId;
        this.personId = personId;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book() {

    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}
