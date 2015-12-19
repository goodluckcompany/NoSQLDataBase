package mainserver;

import workserver.Item;
import workserver.ResponseItem;

import java.io.Serializable;
import java.util.Iterator;

/** Класс служит для инкапсуляции информации для передачи по сети. <br>
 * Поля класса: <br>
 * {@link Request#to},
 * {@link Request#nosqlR}
 * {@link Request#size},
 * {@link Request#isOriginal},
 * {@link Request#reqItems},
 * {@link Request#nameTable}<br>
 *  Методы класса: <br>
 * {@link Request#Request(String, String)}, {@link Request#getSize()},
 * {@link Request#setSize(long)},
 * {@link Request#getNameTable()},
 * {@link Request#setIsOriginal(boolean)},
 * {@link Request#setReqItems(ResponseItem)},
 * {@link Request#getReqItems()},
 * {@link Request#setNosqlR(String)},
 * {@link Request#getNosqlR()},
 * {@link Request#toString()},
 * {@link Request#getTo()},
 * {@link Request#setNameTable(String)}, {@link Request#getIsOriginal()}<br>
 * @author Maslov Nikita
 */
public class  Request  implements Serializable {
    /** Адрес получателя запроса в формате IPv4*/
    String to;
    /** Команда*/
    String nosqlR;
    /** Размер*/
    long size;
    /** Флаг проверки, пришел запрос с основоного сервера таблицы или нет*/
    boolean isOriginal;
    /** Список возвращаемых элементов с рабочего сервера*/
    ResponseItem reqItems;
    /** Имя таблицы*/
    String nameTable = "";

    /** @return size */
    public long getSize(){
        return size;
    }
    /** @param s size */
    public void setSize(long s){
        size = s;
    }

    /** @return nameTable */
    public String getNameTable(){
        return nameTable;
    }

    /** @param _nameTable nameTable */
    public void setNameTable(String _nameTable){
        nameTable = _nameTable;
    }

    /** @return isOriginal */
    public boolean getIsOriginal(){
        return isOriginal;
    }

    /** @param _isOriginal isOriginal */
    public void setIsOriginal(boolean _isOriginal){
        isOriginal = _isOriginal;
    }

    /** @param items reqItems */
    public void setReqItems(ResponseItem items){
        reqItems = items;
    }

    /** @return reqItems */
    public ResponseItem getReqItems(){
        return reqItems;
    }

    /** @param str nosqlR */
    public void setNosqlR(String str){
        nosqlR = str;
    }

    /** @return nosqlR */
    public String getNosqlR(){
        return nosqlR;
    }

    /**Создается объект класса {@link Request}, инициализируются параметры <br>
     * {@link Request#to}, {@link Request#nosqlR} */
    public Request(String _to,String _nosqlR){
        to = _to;
        nosqlR = _nosqlR;
    }
    /**Переопределяется метод toString()*/
    @Override
    public String toString() {
        String tmp ="";
        if(reqItems != null) {
            Iterator<Item> itr = reqItems.ResponseItemList.iterator();
            while (itr.hasNext()){
                tmp += itr.next().toString();
            }
        }
        return getTo() + " : " + getNosqlR() + "\n" + tmp;
    }

    /** @return to */
    public String getTo() {
        return to;
    }
}
