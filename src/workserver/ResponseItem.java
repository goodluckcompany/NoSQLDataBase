package workserver;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by алексей on 29.11.2015.
 */
public class ResponseItem implements Serializable {
    public ArrayList<Item> ResponseItemList;

    public ResponseItem(){
        ResponseItemList = new ArrayList<>();
    }
}
