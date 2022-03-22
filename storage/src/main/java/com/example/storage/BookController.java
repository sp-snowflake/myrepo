package com.example.storage;

import com.qf.cmf.demo.service.Book;
import com.qf.service.api.IBookController;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


//  原本类和方法的注解基本都不用写了。这里只需要实现方法即可。
@Controller
public class BookController implements IBookController {

    /**
     *  ids 是调用方法传来的参数，这个参数格式是自定义的。
     * @param ids
     * @return
     */
    @GetMapping("/books")
    @ResponseBody
    public List<Book> getBooksByIds(String ids){
//        调用方传来的 id
        List<Integer> list = Arrays.stream(ids.split(",")).map(id -> Integer.parseInt(id)).collect(Collectors.toList());
        List<Book> books = new ArrayList<>();
        for (Integer id:list) {
            Book b = new Book();
            b.setId(id);
            books.add(b);
        }
        System.out.println("ids: "+ids);
        return books;
    }

    @Override
    public Book getBookById(Integer id){
        Book book = new Book();
        book.setId(id);
        return book;
    }

    @Override
    public Book addBook(Book book){
        return book;
    }

    @PostMapping("/add")
    public String addBook2(@RequestBody Book book){
        System.out.println("add2 ："+book);
        return "redirect:/index";
    }

    @Override
    public void updateBook (Book book){
        System.out.println("book:"+book);
    }

    @Override
    public void deleteBookById (Integer id){
        System.out.println("id = "+id);
    }





}
