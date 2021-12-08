package com.example.demo.BookOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookOrderRepository
        extends JpaRepository<BookOrder,Long> {

    Optional<BookOrder> findBookById(Long id);

    @Modifying
    @Query("update BookOrder u set u.returned = ?1 where u.id = ?2")
    void setOrderReturned(Boolean borrowed, Long id);

    @Modifying
    @Query("delete BookOrder u  where u.id = ?1")
    void deleteOrder( Long id);

}
