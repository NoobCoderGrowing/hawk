package hawk.index.core;

import hawk.common.core.anlyzer.Anlyzer;
import hawk.common.core.anlyzer.ProbAnlyzer;
import hawk.common.entity.Product;
import hawk.index.service.ProductService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Data
@Slf4j
public class IndexFlow {

    @Resource
    AtomicInteger CASLock;

    @Resource
    @Qualifier("indexExecutor")
    ThreadPoolExecutor indexExecutor;

    @Resource
    ProductService productService;

    private Anlyzer anlyzer = new ProbAnlyzer();

    @Value("${user.dir}")
    private String basePath;

    @Value("${index.subPath:/data2/index/}")
    private String subPath;

    @Async
    public void start (){
        try {
            List<Product> productList = getProductList();
            makeIndexFolder();
            List<IndexJob> indexJobs = parallelIndexing(productList);
            Map<String, ArrayList<Position>> invetedIndex = generateInvertedIndex(indexJobs);
            Map<Integer, String> forwardIndex = generateForwardIndex(indexJobs);
            uploadIndexFile();
        }catch (Exception e){
            log.error("met exception during index creation and index lock is released" + e.getMessage());
        }finally {
            CASLock.set(0);
        }
    }

    //create the index folder
    public void makeIndexFolder(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        String dayIndexPath = basePath + subPath + format;
        File file = new File(dayIndexPath);
        if(!file.exists()){
            boolean result = file.mkdirs();
            if(!result){
                log.error("create index folder failed");
            }
        }
    }

    // load index source data from database
    public List<Product> getProductList(){
        List<Product> list = productService.queryAllProduct();
        if(list==null || list.size()==0){
            log.error("loading product list failed");
        }
        return list;
    }

    // generate index in memory parallel
    public List<IndexJob> parallelIndexing(List<Product> productList){
        List<List<Product>> splittedList = splitList(productList);
        List<Future<IndexJob>> futureList = new ArrayList<>();
        List<IndexJob> result = new ArrayList<>();
        for (int i = 0; i < splittedList.size(); i++) {
            IndexCallable indexCallable = new IndexCallable(splittedList.get(i));
            Future<IndexJob> future = indexExecutor.submit(indexCallable);
            futureList.add(future);
        }
        for (int i = 0; i < splittedList.size(); i++) {
            try {
                IndexJob indexJob = futureList.get(i).get();
                result.add(indexJob);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * The method split product list according to the threadPool size in order to do parallel indexing later
     * @param productList
     * @return
     */
    public List<List<Product>> splitList(List<Product> productList){
        int cores = indexExecutor.getCorePoolSize();
        int rest = productList.size()%cores;
        int count = rest==0?productList.size()/cores:(productList.size()/cores)+1;
        int rolls = rest;
        List<List<Product>> result = new ArrayList<>();
        if(rest == 0){
            for (int i = 0; i < cores; i++) {
                result.add(productList.subList(i*count,(i+1)*count));
            }
        }else{// I wrote two for loop below instead of one loop with if condition as the former is more efficient
            //in runtime though it looks a bit lengthy
            for (int i = 0; i < rolls; i++) {
                result.add(productList.subList(i*count,(i+1)*count));
            }
            for (int i = rolls; i < cores; i++) {
                result.add(productList.subList(i*(count-1), (i+1)*(count-1)));
            }
        }
        return result;
    }

    public Map<String, ArrayList<Position>> generateInvertedIndex(List<IndexJob> indexJobs){
        return null;
    }

    public Map<Integer, String> generateForwardIndex(List<IndexJob> indexJobs){
        return null;
    }

    public void generateIndexFile(Map<String, ArrayList<Position>> invetedIndex, Map<Integer, String> forwardIndex){

    }

    public void uploadIndexFile(){}
}