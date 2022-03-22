import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.qf.cmf.demo.service.Book;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

public class HelloCommand extends HystrixCommand<String> {
    RestTemplate restTemplate;

    Integer id;

    public HelloCommand(RestTemplate restTemplate) {
//        下面主要是配置线程相关的东西。
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("zhangsan")));
        this.restTemplate = restTemplate;
    }

//    public HelloCommand(RestTemplate restTemplat,Integer id) {
//        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("zhangsan")));
//        this.restTemplate = restTemplate;
//        this.id = id;
//    }

    /**
     *  HelloCommand 会开启一个子线程来执行这个 run 方法。
     * @return
     */
    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://storage/duduct",String.class);
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

    @Override
    protected String getCacheKey() {
        return super.getCacheKey();
    }

    //    /**
//     *  这是服务降级的方法，即 run 方法执行失败的时候，会自动触发该方法的执行
//     * @return
//     */
//    protected String getFallback(){
//        return "super.getFallback()";
//    }

//    @Override
//    protected String run() {
//        Book b = restTemplate.getForObject("http://storage/book/?id={1}", Book.class,id);
//        System.out.println("b :"+b);
//        return "aaa";
//    }

//    @Override
//    protected String getCacheKey() {
//        return String.valueOf(id);
//    }
}
