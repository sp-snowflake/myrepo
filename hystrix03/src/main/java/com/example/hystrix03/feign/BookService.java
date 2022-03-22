package com.example.hystrix03.feign;


import com.qf.cmf.demo.service.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


//  fallback = BookServiceFallBack.class 表示处理服务降级的类
@FeignClient(value = "storage",fallback = BookServiceFallBack.class)
public interface BookService {

    @GetMapping("/book/")
    Book getBookById(@RequestParam("id") Integer id);

}
