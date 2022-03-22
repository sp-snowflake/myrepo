package com.example.client01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
// 标记当前项目是一个 eureka 的客户端
@EnableEurekaClient
public class Client01Application {

    public static void main(String[] args) {
        SpringApplication.run(Client01Application.class, args);
    }

}
