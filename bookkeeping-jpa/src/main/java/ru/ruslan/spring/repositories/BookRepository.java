package ru.ruslan.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.spring.models.Book;
import ru.ruslan.spring.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwnerId(int ownerId);
    List<Book> findByTitleStartingWith(String title);
}
