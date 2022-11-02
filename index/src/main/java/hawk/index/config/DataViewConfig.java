package hawk.index.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataViewConfig {

    @Bean
    public DataView getDataView(){
        DataView dataView = new DataView();
        dataView.init();
        return dataView;
    }
}
