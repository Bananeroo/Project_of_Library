package com.example.demo.BookOrder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class BookOrder  implements Serializable {
    @Id
    @SequenceGenerator(
            name = "orders_sequence",
            sequenceName = "orders_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orders_sequence"
    )
    private Long id;
    private Long client_id;
    private Long book_id;
    private String client_name;
    private String book_name;
    private Boolean returned;

    public BookOrder(Long client_id, Long book_id, String client_name, String book_name, Boolean returned) {
        this.client_id = client_id;
        this.book_id = book_id;
        this.client_name = client_name;
        this.book_name = book_name;
        this.returned = returned;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public BookOrder() {
    }

    public BookOrder(
            Long id,
            Long client_id,
            Long book_id,
            Boolean returned) {
        this.id=id;
        this.client_id = client_id;
        this.book_id = book_id;
        this.returned = returned;
    }
    public BookOrder(
            Long client_id,
            Long book_id,
            Boolean returned) {
        this.client_id = client_id;
        this.book_id = book_id;
        this.returned = returned;
    }

    public BookOrder(Long client_id,
                     Long book_id) {
        this.client_id = client_id;
        this.book_id = book_id;
        this.returned = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public Long getBook_id() {
        return book_id;
    }

    public void SetBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void SetReturned(Boolean returned) {
        this.returned = returned;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client_id='" + client_id + '\'' +
                ", book_id='" + book_id + '\'' +
                ", returned='" + returned + '\'' +
                '}';
    }
}
