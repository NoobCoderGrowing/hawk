package hawk.index.core;

import hawk.common.core.anlyzer.Anlyzer;
import hawk.common.core.anlyzer.ProbAnlyzer;
import hawk.common.entity.Product;
import lombok.Data;
import java.util.List;
import java.util.concurrent.Callable;

@Data
public class IndexCallable implements Callable {

    private List<Product> productList;

    private Anlyzer anlyzer;

    public IndexCallable(List<Product> productList) {
        this.productList = productList;
        this.anlyzer = new ProbAnlyzer();
    }

    @Override
    public IndexJob call() throws Exception {
        if(productList != null){
            for (int i = 0; i < productList.size(); i++) {
                indexOneProduct(productList.get(i));
            }
        }


        return null;
    }

    public void indexOneProduct(Product product){

    }
}
