package com.example.demo.BookOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/nopeorders")
public class BookOrderController {

    private final BookOrderService bookOrderService;

    @Autowired
    public BookOrderController(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }




    @GetMapping
    public List<BookOrder> getOrders(){
        return bookOrderService.getOrders();
    }
    @GetMapping(path="/search")
    public List<BookOrder> getOrder(@RequestBody BookOrder bookOrder){
        return (List<BookOrder>) bookOrderService.getOrder(bookOrder);
    }

    @PostMapping(path="/add")
    public void registerNewOrder(@RequestBody BookOrder bookOrder){
        bookOrderService.addNewOrder(bookOrder);
    }


}
