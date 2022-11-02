package hawk.index.config;

import hawk.common.core.anlyzer.CompleteAnlyzer;
import hawk.common.core.anlyzer.ProbAnlyzer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnlyzerConfig {

    @Bean
    public CompleteAnlyzer getCompleteAnlyzer(){
        CompleteAnlyzer completeAnlyzer = new CompleteAnlyzer();
        completeAnlyzer.init();
        return completeAnlyzer;
    }

    @Bean
    public ProbAnlyzer getProbAnlyzer(){
        ProbAnlyzer probAnlyzer = new ProbAnlyzer();
        probAnlyzer.init();
        return probAnlyzer;
    }
}
