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
    public boolean isSuccess(){
        return success;
    }
    public String getAnswer(){
        return answer;
    }

    public void setNosqlR(String str){
        nosqlR = str;
    }
    public String getNosqlR(){
        return nosqlR;
    }

    public Request(String _to,String _data){
        to = _to;
        data = _data;
        nosqlR = "create table1";
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
