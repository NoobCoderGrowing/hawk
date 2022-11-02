package hawk.engine.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import hawk.common.entity.Product;
import hawk.engine.service.SearchService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    RestTemplate restTemplate;


    @Override
    @SentinelResource(value = "search-service", blockHandler = "blocked")
    public ArrayList<Product> search() {
        Product[] result = restTemplate.getForObject("http://index/all",Product[].class);
        return new ArrayList<Product>(Arrays.asList(result));
    }

    public ArrayList<Product> blocked(BlockException e) {
        System.out.println("執行補救措施");
        Product product = new Product();
        product.setCategory("unkown");
        product.setProduct_id(-1);
        product.setProduct_name("unkown");
        product.setCategory_id(-1);
        product.setPrice(-1);
        ArrayList<Product> productArrayList = new ArrayList<>();
        productArrayList.add(product);
        return productArrayList;
    }

}
