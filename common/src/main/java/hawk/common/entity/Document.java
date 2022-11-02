package hawk.common.entity;
import lombok.Data;

@Data
public class Document {
    private long docID;
    private long productID;
    private String productName;
    private String category;
    private long categoryID;
    private double price;
    private long score;
}
