package com.example.demo.Book;

import com.example.demo.Client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository
        extends JpaRepository<Book,Long> {

    List<Book> findAllByTitle(String title);
    Book findByTitle(String title);
    Book findBookById(Long id);
    @Query("SELECT DISTINCT a FROM Book a")
    Collection<Book> findAllDistinct();

    @Modifying
    @Query("update Book u set u.borrowed = ?1 where u.id = ?2")
    void setBookInfoById(Boolean borrowed, Long userId);


}
