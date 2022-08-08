package com.codeWithAshith.SpringBootJUnitRestApiApplication.controller;

import com.codeWithAshith.SpringBootJUnitRestApiApplication.entity.Book;
import com.codeWithAshith.SpringBootJUnitRestApiApplication.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable Long bookId) {
        return bookRepository.findById(bookId).get();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping
    public Book updateBook(@RequestBody Book book) throws Exception {
        if (book == null || book.getBookId() == null) {
            throw new Exception("Book or Book id not found");
        }
        Optional<Book> optionalBook = bookRepository.findById(book.getBookId());
        if (!optionalBook.isPresent()) {
            throw new Exception("Book with id " + book.getBookId() + " does not exit");
        }

        Book existingBook = optionalBook.get();
        existingBook.setName(book.getName());
        existingBook.setSummary(book.getSummary());
        existingBook.setRating(book.getRating());

        return bookRepository.save(existingBook);
    }

    @DeleteMapping("/{bookId}")
    public void deleteById(@PathVariable Long bookId) throws Exception {
        if (!bookRepository.findById(bookId).isPresent()) {
            throw new Exception("Book with id " + bookId + " does not exit");
        }
        bookRepository.deleteById(bookId);
    }

}
