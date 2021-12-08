package com.example.demo.BookOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookOrderService {
    private final BookOrderRepository bookOrderRepository;

    @Autowired
    public BookOrderService(BookOrderRepository bookOrderRepository) {
        this.bookOrderRepository = bookOrderRepository;
    }

    public List<BookOrder> getOrders(){
        return bookOrderRepository.findAll();
    }
    public Object getOrder(BookOrder bookOrder){
        return bookOrderRepository.findBookById(bookOrder.getId());
    }

    public void addNewOrder(BookOrder bookOrder){
        bookOrderRepository.save(bookOrder);
    }

    public void BooksetID(Boolean returned,Long id){
        bookOrderRepository.setOrderReturned(returned,id);
    }
    public void BookdeleteID(Long id){
        bookOrderRepository.deleteOrder(id);
    }

}
