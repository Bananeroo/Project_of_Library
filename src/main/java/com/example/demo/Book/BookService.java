package com.example.demo.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }
    public Book getBook(Long id){
        return bookRepository.findBookById(id);
    }
    public   List<Book> getBookByTitle(String title){
        return bookRepository.findAllByTitle(title);
    }
    public   Book getBookTitle(String title){
        return bookRepository.findByTitle(title);
    }

    public void addNewBook(Book book){
        bookRepository.save(book);
    }

    public List<Book> getBooksDistinct(){
         List<Book> book= (List<Book>) bookRepository.findAllDistinct();
         return book;
    }
    public void borrowBook(Boolean borrowed,Long id){
        bookRepository.setBookInfoById(borrowed, id);
    }

}
