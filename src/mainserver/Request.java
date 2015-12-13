package mainserver;

import workserver.ResponseItem;

import java.io.Serializable;

/**
 * Created by homie on 22.11.2015.
 */
public class  Request  implements Serializable {
    String to;
    String nosqlR;
    String answer;
    ResponseItem reqItems;
    public void setReqItems(ResponseItem items){
        reqItems = items;
    }
    public ResponseItem getReqItems(){
        return reqItems;
    }
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

    public Request(String _to,String _nosqlR){
        to = _to;
        nosqlR = _nosqlR;
    }

    @Override
    public String toString() {
        return getTo() + " : " + getNosqlR();
    }

    public String getTo() {
        return to;
    }
}
