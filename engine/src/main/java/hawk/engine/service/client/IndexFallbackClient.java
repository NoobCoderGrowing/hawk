package hawk.engine.service.client;

import hawk.common.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class IndexFallbackClient implements IndexClient{
    @Override
    public ArrayList<Product> getALLProduct() {
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
