package mainserver;

import java.io.Serializable;

/**
 * Created by homie on 22.11.2015.
 */
public class  Request  implements Serializable {
    String to;
    String data;
    String nosqlR;
    String answer;
    boolean success = false;
    boolean isSuccess(){
        return success;
    }
    String getAnswer(){
        return answer;
    }
    public Request(String _to,String _data){
        to = _to;
        data = _data;
        nosqlR = "create table1";
    }
    public String getNosqlR(){
        return nosqlR;
    }

    @Override
    public String toString() {
        return getTo() + " : " + getData();
    }

    public String getData() {
        return data;
    }

    public String getTo() {
        return to;
    }
}
