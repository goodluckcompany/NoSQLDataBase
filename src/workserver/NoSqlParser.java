package workserver;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Anderson on 25.11.2015.
 */
public class NoSqlParser
{

    StringTokenizer sb;
    String result;
    String start = "";
    String key = "";
    String value = "";
    String nameTable = "";
    public ResponseItem execute (String sql,List<NoSqlDB> listDb, ResponseItem items){
        sb = new StringTokenizer(sql);
        start = sb.nextToken();
        if (start.equals("create")){
            nameTable = sb.nextToken();
            listDb.add(new NoSqlDB(nameTable));
            result = "success";
            items.ResponseItemList.add(new Item("create",result));
        }
        else {
            result = "error";
            items.ResponseItemList.add(new Item("create",result));
        }
        return items;
    }
    public ResponseItem execute(String sql,NoSqlDB db, ResponseItem items){
        sb = new StringTokenizer(sql);
        start = sb.nextToken();
        switch (start){
            case "size" : start = "size";
               items.ResponseItemList.add(new Item("size", Long.toString(db.getFileSize())));
                break;
            case "output" : start = "output";
                start = sb.nextToken();
                if (start.equals("value")){
                    if (sb.nextToken().equals("key"))
                        key = sb.nextToken();
                    nameTable = sb.nextToken();
                    items = db.getValue(key);
                    // вывоз соответствующей функции вывода значения ключа в таблице db
                    break;
                }
                if (start.equals("all")){
                    if (sb.nextToken().equals("key") && sb.nextToken().equals("value"))
                        value = sb.nextToken();
                    nameTable = sb.nextToken();
                    items = db.getKeys(value);
                    // вывоз соответствующей функции вывода всех ключей с заданным значением в таблице db
                    break;
                }
                System.out.println("ERRORout");
                break;
            case "add": start = "add";
                start = sb.nextToken();
                if (start.equals("key")) {
                    key = sb.nextToken();
                    if (sb.nextToken().equals("value"))
                        value = sb.nextToken();
                    nameTable = sb.nextToken();
                    // пусть возвращает 1 в случае успеха, 0 в случае провала
                    db.append(key, value);
                    items.ResponseItemList.add(new Item("added", "true"));
                    // вывоз соответствующей функции добавления ключа со значением в таблицу db
                    break;
                }
                System.out.println("ERRORadd");
                break;
            case "delete": start = "delete";
                start = sb.nextToken();
                if (start.equals("key")){
                    key = sb.nextToken();
                    nameTable = sb.nextToken();
                    db.delKey(key);
                    items.ResponseItemList.add(new Item(key,"not found"));
                    // вывоз соответствующей функции удаления ключа в таблице db
                    break;
                }
                if (start.equals("all")){
                    if (sb.nextToken().equals("key"))
                        if (sb.nextToken().equals("value")){
                            value = sb.nextToken();
                            nameTable = sb.nextToken();
                            db.delValue(value);
                            // вывоз соответствующей функции удаления всех ключей с заданным значением в таблице db
                            items.ResponseItemList.add(new Item("delete all key","success"));
                            break;
                        }
                    System.out.println("ERRORdelAll");
                }
                nameTable = start;
                db.deleteFile();
                db = null; // может придется добавить код в ворк сервер
                items.ResponseItemList.add(new Item(nameTable,"has been deleted"));
                // вывоз соответствующей функции удаления таблицы db
                System.out.println("ERRORdel");
                break;
            case "download": start = "download";
                nameTable = sb.nextToken();
                ResponseItem its = db.getAll();
                // вывоз соответствующей функции возвращения таблицы db
                return its;
            case "create": start = "create";
                nameTable = sb.nextToken();
                db = new NoSqlDB(nameTable);
                // вывоз соответствующей функции создания таблицы db
                items.ResponseItemList.add(new Item("create","true"));
                break;
            default:
                System.out.println("DEF ERROR");
                break;
        }
        return items;
    }
    public static void main(String[] args) {

    }
}

