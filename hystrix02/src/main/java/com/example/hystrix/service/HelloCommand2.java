package com.example.hystrix.service;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

public class HelloCommand2 extends HystrixCommand<String> {

    RestTemplate restTemplate;

    Integer id;


    public HelloCommand2(RestTemplate restTemplate, Integer id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("zhangsan")));
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);  //  这么写意思就是 id 是缓存的 key。
    }

    /**
     *  HelloCommand 会开启一个子线程来执行这个 run 方法。
     * @return
     */
    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://storage/deduct",String.class);
    }

    /**
     *  这是服务降级的方法，即 run 方法执行失败的时候，会自动触发该方法的执行
     * @return
     */
    @Override
    protected String getFallback(){
//        获取执行时的异常（即 run 方法抛出的异常）。
        Throwable throwable = getExecutionException();
        return "error-----fallback--->"+throwable;
    }
}
