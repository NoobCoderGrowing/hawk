package hawk.common.entity;
import lombok.Data;

@Data
public class Product {
    public long product_id;
    public String product_name;
    public String category;
    public long category_id;
    public float price;
}
