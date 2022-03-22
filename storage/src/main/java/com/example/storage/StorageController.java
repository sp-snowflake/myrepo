package com.example.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {

    @Value("${server.port}")
    Integer port;

    /**
     *  商品扣库存
     * @return
     */
    @GetMapping("/deduct")
    public String deduct(){
        System.out.println("deduct");
        return "hello deduct :"+port;
    }


}
