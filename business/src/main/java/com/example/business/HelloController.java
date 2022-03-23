package com.example.business;

import com.qf.cmf.demo.service.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class HelloController {

//    注意这里使用 Spring Cloud 中的接口
//    通过 DiscoveryClient 可以在 Eureka 上查询不同服务的信息
    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    AtomicInteger count = new AtomicInteger(1);

    /**
     * 这是一个用户下单的接口
     */
    @GetMapping("/hello")
    public void hello () throws IOException {
//        根据服务名查询服务信息，由于 storage 可能是集群化部署，所以返回的是一个 List 集合
        List<ServiceInstance> list = discoveryClient.getInstances("storage");

//        获取一个实例对象
//                                          每次获取旧值，再加 1。然后跟 list 大小取余。
        ServiceInstance instance = list.get(count.getAndAdd(1) % list.size() );

//        获取 storage 服务的主机地址
        String host = instance.getHost();

//        获取 storage 服务的端口号
        int port = instance.getPort();
        URL url = new URL("http://" + host + ":" + port + "/deduct");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        建立链接
        con.connect();
        if (con.getResponseCode() == 200){
//            说明请求成功
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String s = br.readLine();
            System.out.println("s:"+s);
            br.close();
        }
    }


    @GetMapping("/hello2")
    public void hello2() {
        List<ServiceInstance> list = discoveryClient.getInstances("storage");
        ServiceInstance instance = list.get(count.getAndAdd(1) % list.size() );
        String host = instance.getHost();
        int port = instance.getPort();
        String url = "http://" + host + ":" + port + "/deduct";
        String s = restTemplate.getForObject(url,String.class);
        System.out.println("s:"+ s);
    }


    @GetMapping("/hello3")
    public void hello3() {
//        带有负载均衡功能的 restTemplate，会自带一个拦截器，这个拦截器会拦截下请求的 URL 地址，然后从中提取
//         出 storage 字符串，提取出来之后（可用正则表达式提取），底层就是通过 DiscoveryClient 去查找该服务的信息，
//         然后获取到服务的地址和端口，再根据地址和端口拼接出来一个完整的请求地址。
        String s = restTemplate.getForObject("http://storage/deduct",String.class);
        System.out.println("s:"+ s);
    }
    
    @GetMapping("/hello4")
    public void hello4 (){
//        两种传参的方式
//        第一种方式：          这里传 id = 99
        ResponseEntity<Book> entity = restTemplate.getForEntity("http://storage/book/?id={1}", Book.class, 99);

//        第二种方式：
        Map<String, Object> params = new HashMap<>();
        params.put("id",99);
        ResponseEntity<Book> entity2 = restTemplate.getForEntity("http://storage/book/?id={id}", Book.class, params);

//        获取接口返回的对象
        Book book = entity2.getBody();
        System.out.println("book =" + book);

//        获取 HTTP 响应状态码
        int statusCodeValue = entity2.getStatusCodeValue();
        System.out.println("statusCodeValue = "+statusCodeValue);

//        这个不是数字，这个是对象。里面是各种枚举。 这个会输出诸如 200 OK 这种信息
        HttpStatus statusCode = entity2.getStatusCode();
        System.out.println("statusCode = "+statusCode);

//        获取 HTTP 响应头
        HttpHeaders headers = entity2.getHeaders();
        Set<String> keySet = headers.keySet();
        for (String key:keySet) {
            List<String> v = headers.get(key);
            System.out.println(key + "---->" + v);
        }
    }

    @GetMapping("/hello5")
    public void hello5 (){
//        注意：这种参数传递形式是 key-value 形式，如果直接创建了一个 book 对象，则参数的传递形式是 JSON 形式，
//         即对方的接口需要有 @RequestBody 注解
        LinkedMultiValueMap<String, Object> p = new LinkedMultiValueMap<>();
        p.add("id",99);
        p.add("name","金瓶梅");
        p.add("author","罗贯中");
        Book b = restTemplate.postForObject("http://storage/book/", p, Book.class);
        System.out.println("b："+ b);

        System.out.println("=============== Location =====================");
//        post 一般用来做添加，添加完成后，一般要重定向到列表页面，所以这个接口的返回值是一个 URI，这是一个重定向的地址。
//        uri 就是要重定向的地址
//        调用这个方法有一个要求：目标接口必须返回重定向，必须是响应 302
        URI uri = restTemplate.postForLocation("http://storage/book/add",b);
        System.out.println("URI = "+uri);

        System.out.println("=====================PUT========================");
        restTemplate.put("http://storage/book/",b);

        System.out.println("=======================DELETE========================");
        restTemplate.delete("http://storage/book/{1}",99);



    }



}




















