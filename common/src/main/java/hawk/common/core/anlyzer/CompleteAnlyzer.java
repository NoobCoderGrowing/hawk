package hawk.common.core.anlyzer;

import hawk.common.core.FieldProperties;
import hawk.common.core.Token;
import javafx.beans.binding.ObjectExpression;

import java.util.List;

public class CompleteAnlyzer implements Anlyzer{
    @Override
    public List<Token> tokenize(String sentence) {
        return null;
    }

    public List<Token> startAnlyze(int docID, Object value, FieldProperties fieldProperties) {
        switch (fieldProperties.getType()){
            case 1:
                value = (Short) value;
                break;
            case 2:
                value = (Integer) value;
                break;
            case 3:
                tokenize(docID, (String) value, fieldProperties);
                break;
            case 4:
                value = (Float) value;
                break;

        }
        return null;
    }

    public List<Token> tokenize(int docID, String value, FieldProperties fieldProperties){

    }

    @Override
    public void init() {

    }
}
