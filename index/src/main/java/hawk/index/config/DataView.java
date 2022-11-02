package hawk.index.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.net.URL;
import java.util.HashMap;

@Data
@Slf4j
public class DataView {

    private HashMap<String, Short> nameToID;

    private HashMap<Short, FieldProperties> IDToFieldProps;

    public DataView() {
        this.nameToID = new HashMap<String, Short>();
        this.IDToFieldProps = new HashMap<Short, FieldProperties>(5);
    }

    // read index.conf file into memory
    public void init(){
        URL fileUrl = getClass().getClassLoader().getResource("index.conf");
        if(fileUrl == null){
            log.error("canot find index.conf");
            System.exit(-1);
        };
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileUrl.getPath()));
            String line = reader.readLine();
            while (line!=null){
                line = line.trim();
                if(line.startsWith("#") || line.isEmpty()){
                    line = reader.readLine();
                    continue;
                }
                String[] values = line.split(",");
                Short id = Short.parseShort(values[0].trim());
                String name = values[1].trim();
                boolean present = values[2].trim().equals("1")?true:false;
                Short beIndex = Short.parseShort(values[3].trim());
                Float weight = Float.parseFloat(values[4].trim());
                nameToID.put(name,id);
                FieldProperties fieldProperties = new FieldProperties(id, name, present, beIndex, weight);
                IDToFieldProps.put(id,fieldProperties);
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            log.error("Open index.conf failed");
            System.exit(-1);
        } catch (IOException e) {
            log.error("Read index.conf failed");
            System.exit(-1);
        }

    }
}
