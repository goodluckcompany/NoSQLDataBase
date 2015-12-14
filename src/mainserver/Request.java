package mainserver;

import workserver.Item;
import workserver.ResponseItem;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by homie on 22.11.2015.
 */
public class  Request  implements Serializable {
    String to;
    String nosqlR;
    long size;
    boolean isOriginal;
    ResponseItem reqItems;
    String nameTable = "";

    public long getSize(){
        return size;
    }
    public void setSize(long s){
        size = s;
    }

    public String getNameTable(){
        return nameTable;
    }

    public void setNameTable(String _nameTable){
        nameTable = _nameTable;
    }

    public boolean getIsOriginal(){
        return isOriginal;
    }

    public void setIsOriginal(boolean _isOriginal){
        isOriginal = _isOriginal;
    }

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
        String tmp ="";
        if(reqItems != null) {
            Iterator<Item> itr = reqItems.ResponseItemList.iterator();
            while (itr.hasNext()){
                tmp += itr.next().toString() + "\n";
            }
        }
        return getTo() + " : " + getNosqlR() + "\n" + tmp;
    }

    public String getTo() {
        return to;
    }
}
