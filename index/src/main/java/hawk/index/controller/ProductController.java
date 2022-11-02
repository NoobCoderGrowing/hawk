package hawk.index.controller;

import hawk.common.entity.Product;
import hawk.index.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
public class ProductController {

    @Resource
    ProductService productService;

    @RequestMapping("/all")
    public ArrayList<Product> getALLProduct(){
        return productService.queryAllProduct();
    }
}
