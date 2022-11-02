package hawk.common.entity;
import lombok.Data;

@Data
public class Product {
    long product_id;
    String product_name;
    String category;
    long category_id;
    double price;
}
