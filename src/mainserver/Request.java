package mainserver;

import java.io.Serializable;

/**
 * Created by homie on 22.11.2015.
 */
public class  Request  implements Serializable {
    static int count = 0;
    String name = "req";

    public Request(){
        name = name + count;
        count++;
    }

    @Override
    public String toString() {
        return name;
    }
}
