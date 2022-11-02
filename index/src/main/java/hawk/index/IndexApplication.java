package hawk.index;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


@SpringBootApplication
@EnableAsync
public class IndexApplication {


    public static void main(String[] args) {
        SpringApplication.run(IndexApplication.class, args);
    }

    @Bean
    public AtomicInteger atomicLock(){
        return new AtomicInteger(0);
    }

    @Bean("indexExecutor")
    public ThreadPoolExecutor indexExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20,20,1,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        return threadPoolExecutor;
    }
}
