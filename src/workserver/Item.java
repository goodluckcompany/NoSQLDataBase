package workserver;

/**
 * Created by алексей on 29.11.2015.
 */
public class Item {
    private String keyField;
    private String valueField;

    public Item(String key, String value){
        keyField = key;
        valueField = value;
    }

    public String getKeyField(){
        return keyField;
    }

    public String getValueField(){
        return valueField;
    }

    public void setKeyField(String key){
        keyField = key;
    }

    public void setValueField(String value){
        valueField = value;
    }

    public String toString(){
        String s = "";
        s = keyField + " : " + valueField + "\n";

        return s;
    }

}
