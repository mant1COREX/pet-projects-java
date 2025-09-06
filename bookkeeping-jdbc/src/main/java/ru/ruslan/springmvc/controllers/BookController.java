package ru.ruslan.springmvc.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.springmvc.dao.BookDAO;
import ru.ruslan.springmvc.dao.PersonDAO;
import ru.ruslan.springmvc.models.Book;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));

        if (bookDAO.show(id).getPersonId() != null) {
            model.addAttribute("person", personDAO.show(bookDAO.show(id).getPersonId()));
        }

        model.addAttribute("people", personDAO.index());
        return "books/show";
    }

    @GetMapping("/new")
    public String add(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "/books/new";

        bookDAO.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "/books/new";

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignToPerson(@PathVariable("id") int bookId, @RequestParam("getPersonId") int personId) {
        bookDAO.assignToPerson(bookId, personId);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release")
    public String releaseFromPerson(@PathVariable("id") int id) {
        bookDAO.releaseFromPerson(id);
        return "redirect:/books/{id}";
    }
}
