package hawk.index.core;

import lombok.Data;

@Data
public class Position {
    private int docID;
    private String filedName;
    private short offset;
}
