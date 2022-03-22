package com.example.openfeign.controller;

import com.example.openfeign.feign.BookService;
import com.qf.cmf.demo.service.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    BookService bookService;

    @GetMapping("/test1")
    public void test1 (){
        Book b1 = bookService.getBookById(1);
        System.out.println("b1:"+b1);
        System.out.println("bookService.addBook(b1):"+bookService.addBook(b1));
//        System.out.println("bookService.addBook2(b1):"+bookService.addBook2(b1));

        bookService.deleteBookById(99);
        bookService.updateBook(b1);


    }




}













