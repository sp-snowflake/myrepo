package com.example.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

//    /**
//     *  远程调用 storage 服务，但是，可能会调用失败。
//     *
//     *  目的：当 storage 调用失败的时候，不影响到当前的方法
//     *
//     *  @HystrixCommand(fallbackMethod = "") 指定了当前方法的断路器，即当前方法如果执行失败，执行抛出异常，会触发
//     *   fallbackMethod 中指定的方法，这种方式叫做服务降级
//     *
//     * @return
//     */
//    @HystrixCommand(fallbackMethod = "error")
//    public String hello(){
////        int i = 1/0;
//        String s = restTemplate.getForObject("http://storage/deduct", String.class);
//        System.out.println("s:"+s);
//        return s;
//    }
//
//    /**
//     *  该方法时 hello 方法的降级方法，error 方法在定义的过程中，返回值需要和 hello 方法保持一致。
//     * @return
//     */
//    public String error(){
//        return "error";
//    }

    /**
     *  远程调用 storage 服务，但是，可能会调用失败。
     *
     *  目的：当 storage 调用失败的时候，不影响到当前的方法
     *
     *  @HystrixCommand(fallbackMethod = "") 指定了当前方法的断路器，即当前方法如果执行失败，执行抛出异常，会触发
     *   fallbackMethod 中指定的方法，这种方式叫做服务降级
     *
     *   ignoreExceptions = ArithmeticException.class 表示忽略的异常，即抛出 ArithmeticException 异常的时候，
     *    不进行服务降级，直接将异常抛出。
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = ArithmeticException.class)
    public String hello(){
        int i = 1 / 0;
        String s = restTemplate.getForObject("http://storage/deduce", String.class);
        System.out.println("s:"+s);
        return s;
    }

    /**
     *  该方法时 hello 方法的降级方法，error 方法在定义的过程中，返回值需要和 hello 方法保持一致。
     *
     *  如果抛异常进入到 error 方法中，那么我们希望能够在 error 方法中获取到具体的异常。
     * @return
     */
    public String error(Throwable t){
        return "error" + t.getMessage();
    }

}
