package com.example.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.qf.cmf.demo.service.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookService2 {

    @Autowired
    RestTemplate restTemplate;

    /**
     *  Hystrix 中自带有请求缓存功能，getBookById 方法执行的结果会被缓存起来;
     *   默认情况下，方法的参数是缓存的 key，方法的返回值是缓存的 value。
     *
     *  .@CacheResult：表示启用缓存
     *
     *  .@CacheKey：可以指定缓存的 key。这里表示 id 字段是缓存的 key，name 字段不是。
     *
     *  .@CacheRemove：表示移除缓存中的数据
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "error")
    @CacheResult    //  表示启用缓存
    public Book getBookById(@CacheKey Integer id, String name){   // @CacheKey 表示该字段是缓存的 key。
        return restTemplate.getForObject("http://storage/book?id={1}",Book.class,99);
    }

    public Book error(Integer id){
        Book book = new Book();
        book.setId(id);
        book.setName("服务降级了");
        return book;
    }

}












