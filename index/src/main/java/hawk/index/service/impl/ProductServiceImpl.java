package hawk.index.service.impl;

import hawk.common.entity.Product;
import hawk.index.mapper.ProductMapper;
import hawk.index.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    ProductMapper productMapper;

    @Override
    public ArrayList<Product> queryAllProduct(){
        return productMapper.queryAllProduct();
    };
}