package com.example.hystrix03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@SpringCloudApplication
@EnableFeignClients
public class Hystrix03Application {

    public static void main(String[] args) {
        SpringApplication.run(Hystrix03Application.class, args);
    }

}
