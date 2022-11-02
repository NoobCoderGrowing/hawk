package hawk.index.core;

import com.alibaba.fastjson.JSON;
import hawk.common.core.anlyzer.CompleteAnlyzer;
import hawk.common.entity.Product;
import hawk.index.config.DataView;
import hawk.common.core.FieldProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Slf4j
public class IndexCallable implements Callable {

    private List<Product> productList;

    @Autowired
    @Qualifier("autoIncreDocumentID")
    AtomicInteger autoIncreDocumentID;


    @Resource
    DataView dataView;

    @Autowired
    @Qualifier("productFields")
    Field[] fields;

    @Resource
    CompleteAnlyzer completeAnlyzer;

    public IndexCallable(List<Product> productList) {
        this.productList = productList;
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
        HashMap<String, Short> nameToID = dataView.getNameToID();
        HashMap<Short, FieldProperties> IDToFieldProps = dataView.getIDToFieldProps();
        int docID = autoIncreDocumentID.addAndGet(1);
        for (int i = 0; i < fields.length; i++) {
            short id = nameToID.get(fields[i].getName());
            FieldProperties fieldProperties = IDToFieldProps.get(id);
            try {
                Object value = fields[i].get(product);
                indexOneField(docID, value,fieldProperties);
            } catch (IllegalAccessException e) {
                log.error("access field value through reflection failed: " + JSON.toJSONString(product));
            }
        }
    }

    public void indexOneField(int docID, Object value, FieldProperties fieldProperties){
        switch (fieldProperties.getBeIndex()){
            case 0:
                break;
            case 1:
                completeAnlyzer.startAnlyze(docID, value, fieldProperties);
                break;
            case 2:
                break;
        }

    }
}
