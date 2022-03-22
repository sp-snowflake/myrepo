package com.example.hystrix.controller;

import com.example.hystrix.service.*;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.qf.cmf.demo.service.Book;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HelloController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HelloService helloService;

    @Autowired
    BookService2 bookService2;

    @Autowired
    BookService bookService;

    @GetMapping("/test2")
    public void test2 () throws ExecutionException, InterruptedException {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        BookCollapseCommand command0 = new BookCollapseCommand(100, bookService);
        BookCollapseCommand command1 = new BookCollapseCommand(101, bookService);
        BookCollapseCommand command2 = new BookCollapseCommand(102, bookService);
        BookCollapseCommand command3 = new BookCollapseCommand(103, bookService);

//        注意，这里只能使用 queue 方法，因为这个方法让请求先入队，然后等待其他请求；如果
//         使用 execute ，就不会等待其他请求。
        Future<Book> f0 = command0.queue();
        Future<Book> f1 = command1.queue();
        Future<Book> f2 = command2.queue();
        Future<Book> f3 = command3.queue();

//        get 方法还可以填时间参数，如果到期了还没有就会报超时。
        Book b0 = f0.get();
        Book b1 = f1.get();
        Book b2 = f2.get();
        Book b3 = f3.get();
        System.out.println("b0 :" + b0);
        System.out.println("b1 :" + b1);
        System.out.println("b2 :" + b2);
        System.out.println("b3 :" + b3);
        ctx.close();

    }




    @GetMapping("/test1")
    public void test1 (){
//        缓存有一个范围
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
//        初始化上下文之后，缓存就开始了
        Book b1 = bookService2.getBookById(99,"zs");
        Book b2 = bookService2.getBookById(99,"ls");
//        到上下文关闭，缓存就结束了。
        ctx.close();
//        凡是带有 @CacheResult 注解的方法，都应该在 ctx 未关闭之前调用。
//        Book b3 = bookService.getBookById(99);
        System.out.println("b1:"+b1);
        System.out.println("b2:"+b2);

    }


    @GetMapping("/hello")
    public String hello (){
//        return helloService.hello();

//        一个 helloCommand 对象只能执行一次
        HelloCommand helloCommand = new HelloCommand(restTemplate);
//        同步执行请求方法，这个会发生阻塞
        String execute = helloCommand.execute();
//        异步调用，不会阻塞。
//        Future<String> future = helloCommand.queue();
        return execute;
    }




}
