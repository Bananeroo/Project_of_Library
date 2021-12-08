package com.example.demo.Book;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Book implements Serializable {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    private Long id;
    private String title;
    private String author;
    @Column(columnDefinition = "TEXT")
    private String bookDescription;
    private Boolean borrowed;

    public Book() {
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    public Book(Long id,
                String title,
                String author,
                String bookDescription, Boolean borrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.bookDescription = bookDescription;
        this.borrowed = borrowed;
    }

    public Book(String title,
                String author,
                String bookDescription, Boolean borrowed) {
        this.title = title;
        this.author = author;
        this.bookDescription = bookDescription;
        this.borrowed = borrowed;
    }
    public Book(String title, String author, String bookDescription) {
        this.title = title;
        this.author = author;
        this.bookDescription = bookDescription;
        this.borrowed=false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Boolean getBorrowed() {
        return borrowed;
    }

    public void setBorrowed() {
        this.borrowed = true;
    }
    public void unsetBorrowed() {
        this.borrowed = false;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", author='" + author + '\'' +
                ", borrowe='" + borrowed + '\'' +

                '}';
    }
}
