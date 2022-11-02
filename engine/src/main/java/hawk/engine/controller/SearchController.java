package hawk.engine.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import hawk.common.entity.Product;
import hawk.engine.service.SearchService;
import hawk.engine.service.client.IndexClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/gateway/engine")
public class SearchController {

    @Resource
    SearchService searchService;

    @Resource
    IndexClient indexClient;


    @RequestMapping("/feign/search")
    ArrayList<Product> feignSearch(){
        return indexClient.getALLProduct();
    }


    @RequestMapping("/search")
    ArrayList<Product> search(HttpServletRequest request){
        System.out.println("正常執行方法");
//        System.out.println(request.getHeader("Test"));
        return searchService.search();
    }

    @RequestMapping("/blocked")
    JSONObject blocked(){
        JSONObject object = new JSONObject();
        object.put("code", 403);
        object.put("success", false);
        object.put("massage", "您的请求频率过快，请稍后再试！");
        return object;
    }

    @RequestMapping("/test")
    @SentinelResource(value = "test",
            fallback = "except",    //fallback指定出现异常时的替代方案
            exceptionsToIgnore = IOException.class)  //忽略那些异常，也就是说这些异常出现时不使用替代方案
    String test(){
        throw new RuntimeException("HelloWorld！");
    }

    String except(Throwable t){
        return t.getMessage();
    }

    @RequestMapping("/hot")
    @SentinelResource(value = "hot")   //注意这里需要添加@SentinelResource才可以，用户资源名称就使用这里定义的资源名称
    String findUserBorrows2(@RequestParam(value = "a", required = false) Integer a,
                            @RequestParam(value = "b", required = false) Integer b,
                            @RequestParam(value = "c",required = false) Integer c) {
        return "请求成功！a = "+a+", b = "+b+", c = "+c;
    }
}
