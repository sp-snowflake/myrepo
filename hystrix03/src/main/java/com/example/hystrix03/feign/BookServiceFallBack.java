package com.example.hystrix03.feign;


import com.qf.cmf.demo.service.Book;
import org.springframework.stereotype.Component;


@Component
public class BookServiceFallBack implements BookService{

    /**
     *  这就是服务降级的方法
     * @param id
     * @return
     */
    @Override
    public Book getBookById(Integer id) {
        Book book = new Book();
        book.setId(id);
        book.setName("服务降级了");
        return book;
    }
}
