package hawk.common.core.anlyzer;

import hawk.common.core.Token;
import org.springframework.stereotype.Component;

import java.util.List;

public interface Anlyzer {
    public List<Token> tokenize(String sentence);
}
