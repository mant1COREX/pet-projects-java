package ru.ruslan.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.spring.models.Book;
import ru.ruslan.spring.services.BookService;
import ru.ruslan.spring.services.PersonService;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping
    public String findAll(
            Model model,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
            @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear) {

        if (page != null && booksPerPage != null) {
            if (Boolean.TRUE.equals(sortByYear)) {
                model.addAttribute("books", bookService.findAllPaginationSort(page, booksPerPage));
            } else {
                model.addAttribute("books", bookService.findAllPagination(page, booksPerPage));
            }

            model.addAttribute("sortByYear", sortByYear);
            model.addAttribute("currentPage", page);
            model.addAttribute("booksPerPage", booksPerPage);
            model.addAttribute("totalPages", bookService.getTotalPages(booksPerPage));
        } else {
            model.addAttribute("books", bookService.findAll());
        }

        return "books/index";
    }

    @GetMapping("{id}")
    public String findOne(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));

        if (bookService.findOne(id).getOwner() != null) {
            model.addAttribute("person", bookService.findOne(id).getOwner());
        }

        model.addAttribute("people", personService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String addBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") Book book) {
        bookService.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @RequestParam("personId") int personId) {
        bookService.assignToPerson(id, personId);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.releaseToPerson(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String getSearch() {
        return "books/search";
    }

    @PostMapping("/search")
    public String searchBook(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", bookService.searchByTitle(query));
        return "books/search";
    }
}
