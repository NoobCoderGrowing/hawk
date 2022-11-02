package hawk.index.mapper;

import hawk.common.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.ArrayList;

@Mapper
public interface ProductMapper {
    @Select("select * from product_info")
    ArrayList<Product> queryAllProduct();
}
