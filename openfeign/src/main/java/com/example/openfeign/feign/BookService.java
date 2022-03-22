package com.example.openfeign.feign;

import com.qf.cmf.demo.service.Book;
import com.qf.service.api.IBookController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;



@FeignClient(value = "storage")
public interface BookService extends IBookController {
//    这样即可。接口啥都不用写，该有的都有了。

}
