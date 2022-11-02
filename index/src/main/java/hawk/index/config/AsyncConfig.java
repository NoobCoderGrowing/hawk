package hawk.index.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {

    @Resource
    AsyncExceptionHandler asyncExceptionHandler;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,
                10, 1, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        return threadPoolExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return asyncExceptionHandler;
    }
}
