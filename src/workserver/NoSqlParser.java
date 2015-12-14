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
        //Удалить
        System.out.println("LOL0");
        //Удалить
        if (start.equals("create")){
            //Удалить
            System.out.println("LOL1");
            //Удалить
            nameTable = sb.nextToken();
            
            listDb.add(new NoSqlDB(nameTable));

            //Удалить
            System.out.println(listDb.get(0).getDbName());
            System.out.println("LOL2");
            //Удалить
            result = "success";
        }
        else {
            //Удалить
            System.out.println("LOL3");
            //Удалить
            result = "error";
        }
        //Удалить
        System.out.println("LOL4");
        //Удалить
        return items;
    }
    public ResponseItem execute(String sql,NoSqlDB db, ResponseItem items){
        sb = new StringTokenizer(sql);
        start = sb.nextToken();
        switch (start){
            case "size" : start = "size";
                items.ResponseItemList.add(new Item("size",Long.toString(db.getFileSize())));
                System.out.println(db.getDbName());
                break;
            case "output" : start = "output";
                System.out.println(start);
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
                System.out.println(start);
                start = sb.nextToken();
                if (start.equals("key")) {
                    key = sb.nextToken();
                    if (sb.nextToken().equals("value"))
                        value = sb.nextToken();
                    nameTable = sb.nextToken();
                    // пусть возвращает 1 в случае успеха, 0 в случае провала
                    db.append(key, value);
                    // вывоз соответствующей функции добавления ключа со значением в таблицу db
                    break;
                }
                System.out.println("ERRORadd");
                break;
            case "delete": start = "delete";
                System.out.println(start);
                start = sb.nextToken();
                if (start.equals("key")){
                    key = sb.nextToken();
                    nameTable = sb.nextToken();
                    db.delKey(key);
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
                            break;
                        }
                    System.out.println("ERRORdelAll");
                }
                nameTable = start;
                db.deleteFile();
                db = null; // может придется добавить код в ворк сервер
                // вывоз соответствующей функции удаления таблицы db
                System.out.println("ERRORdel");
                break;
            case "download": start = "download";
                System.out.println(start);
                nameTable = sb.nextToken();
                items = db.getAll();
                // вывоз соответствующей функции возвращения таблицы db
                break;
            case "create": start = "create";
                System.out.println(start);
                nameTable = sb.nextToken();
                db = new NoSqlDB(nameTable);
                // вывоз соответствующей функции создания таблицы db
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

