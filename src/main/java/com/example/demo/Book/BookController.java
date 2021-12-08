package com.example.demo.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/looged/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public List<Book> getBooks(){
        return bookService.getBooks();
    }


    @PostMapping(path="/add")
    public void registerNewBook(@RequestBody Book book){
        bookService.addNewBook(book);
    }


}
