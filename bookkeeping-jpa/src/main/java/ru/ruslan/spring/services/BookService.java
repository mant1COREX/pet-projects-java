package ru.ruslan.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruslan.spring.models.Book;
import ru.ruslan.spring.models.Person;
import ru.ruslan.spring.repositories.BookRepository;
import ru.ruslan.spring.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAllPagination(Integer page, Integer booksPerPage) {
        return bookRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }

    public List<Book> findAllPaginationSort(Integer page, Integer booksPerPage) {
        return bookRepository.findAll(PageRequest.of(page,booksPerPage, Sort.by("year"))).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void create(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updateBook) {
        updateBook.setId(id);
        bookRepository.save(updateBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findBooks(int id) {
        return bookRepository.findByOwnerId(id);
    }

    @Transactional
    public void assignToPerson(int bookId, int personId) {
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Person> person = personRepository.findById(personId);

        if (book.isPresent() && person.isPresent()) {
            book.get().setOwner(person.get());
            bookRepository.save(book.get());
        }
    }

    @Transactional
    public void releaseToPerson(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        foundBook.ifPresent(book -> book.setOwner(null));
    }

    public int getTotalPages(int booksPerPage) {
        long count = bookRepository.count();
        return (int) Math.ceil((double) count / booksPerPage);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleStartingWith(title);
    }
}
