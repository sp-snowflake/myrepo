package com.example.hystrix03.controller;

import com.example.hystrix03.feign.BookService;
import com.qf.cmf.demo.service.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    BookService bookService;

    @GetMapping("/test")
    public Book getBookById(){
        return bookService.getBookById(99);
    }         


}
