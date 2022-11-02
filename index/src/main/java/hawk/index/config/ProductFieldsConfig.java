package hawk.index.config;

import hawk.common.entity.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;

@Configuration
public class ProductFieldsConfig {

    @Bean("productFields")
    public Field[] getProductFields(){
        return Product.class.getDeclaredFields();
    }
}
