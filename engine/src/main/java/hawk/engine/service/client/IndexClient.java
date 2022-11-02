package hawk.engine.service.client;

import hawk.common.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@FeignClient(value = "index", fallback = IndexFallbackClient.class)
public interface IndexClient {

    @RequestMapping("/all")
    public ArrayList<Product> getALLProduct();
}
