package hawk.index.config;

import lombok.Data;

@Data
public class FieldProperties {

    private short id;

    private String name;

    private boolean present;

    private short beIndex;

    private float weight;

    public FieldProperties(short id, String name, boolean present, short beIndex, float weight) {
        this.id = id;
        this.name = name;
        this.present = present;
        this.beIndex = beIndex;
        this.weight = weight;
    }
}
