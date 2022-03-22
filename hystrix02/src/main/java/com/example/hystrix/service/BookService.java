package com.example.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.qf.cmf.demo.service.Book;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

   @Autowired
    RestTemplate restTemplate;

   public List<Book> getBookByIds(List<Integer> ids){
//       因为真正到 http 请求里面是 json 数组，所以这里也是 json 数组，所以用 Book[] 这种方式。
       Book[] books = restTemplate.getForObject("http://storage/books?ids={1}", Book[].class, StringUtils.join(ids,","));
       return Arrays.asList(books);
   }

}












