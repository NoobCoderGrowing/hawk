package hawk.common.core.anlyzer;

import hawk.common.core.Token;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Data
public class ProbAnlyzer implements Anlyzer{

    private String LMPath;

    public ArrayList<Token> tokenize (String sentence){
        return new ArrayList<>();
    }
}
