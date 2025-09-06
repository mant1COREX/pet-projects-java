package ru.ruslan.spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty(message = "Название книги не должно быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов")
    @Pattern(regexp = "^[А-Яа-яЁёA-Za-z0-9 ,.!?\\-:;\"'()]{2,100}$", message = "Название книги может содержать буквы, цифры, пробелы и знаки пунктуации")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Имя автора не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора должно быть от 2 до 100 символов")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+(?:-[А-ЯЁ][а-яё]+)?( [А-ЯЁ][а-яё]+){1,2}$", message = "Имя автора должно быть в формате: Пушкин Александр Сергеевич")
    private String author;

    @Column(name = "year")
    @Min(value = 1500, message = "Год должен быть не меньше 1500")
    @Max(value = 2025, message = "Год не может быть больше текущего")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book() {

    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
