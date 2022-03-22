package com.example.hystrix.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.qf.cmf.demo.service.Book;

import java.util.List;

public class BookBatchCommand extends HystrixCommand<List<Book>> {

    private List<Integer> ids;
    private BookService bookService;


    public BookBatchCommand(List<Integer> ids, BookService bookService) {
//        跟之前一样，都是线程里面的东西。
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batchCmd"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("batchkey")));
        this.ids = ids;
        this.bookService = bookService;
    }

    @Override
    protected List<Book> run() throws Exception {
        return bookService.getBookByIds(ids);
    }

    @Override
    protected List<Book> getFallback() {
        return null;
    }
}
