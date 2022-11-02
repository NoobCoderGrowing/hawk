package hawk.index.controller;

import hawk.index.core.IndexFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
@Slf4j
public class IndexController {

    @Resource
    AtomicInteger CASLock;

    @Resource
    IndexFlow indexFlow;

    @Resource
    ThreadPoolExecutor executor;

    @RequestMapping("/createIndex")
    public String createIndex(){
        if(CASLock.compareAndSet(0,1)){
            indexFlow.start();
            return "Sucessfully acquired and lock and indexing flow is started.";
        }
        return "Fail to acquire the indexing lock.";
    }
}
