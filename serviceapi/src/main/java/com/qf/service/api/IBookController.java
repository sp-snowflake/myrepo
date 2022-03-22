package com.qf.service.api;

import com.qf.cmf.demo.service.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


public interface IBookController {

    @GetMapping("/book/")
    @ResponseBody
    Book getBookById(@RequestParam("id") Integer id);

    @PostMapping("/book/")
    @ResponseBody
    Book addBook(@RequestBody Book book);

    @PostMapping("/book/add")
    @ResponseBody
    String addBook2(@RequestBody Book book);

    @ResponseBody
    @PutMapping("/book/")
    void updateBook (@RequestBody Book book);

    @DeleteMapping("/book/{id}")
    @ResponseBody
    void deleteBookById (@PathVariable("id") Integer id);



}
