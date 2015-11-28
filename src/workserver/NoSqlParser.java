package workserver;

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
    public String execute(String sql){
        sb = new StringTokenizer(sql);
        start = sb.nextToken();
        switch (start){
            case "output" : start = "output";
                System.out.println(start);
                start = sb.nextToken();
                if (start.equals("value")){
                    if (sb.nextToken().equals("key"))
                        key = sb.nextToken();
                    nameTable = sb.nextToken();
                    // вывоз соответствующей функции вывода значения ключа в таблице db
                    break;
                }
                if (start.equals("all")){
                    if (sb.nextToken().equals("key") && sb.nextToken().equals("value"))
                        value = sb.nextToken();
                    nameTable = sb.nextToken();
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
                    // вывоз соответствующей функции удаления ключа в таблице db
                    break;
                }
                if (start.equals("all")){
                    if (sb.nextToken().equals("key"))
                        if (sb.nextToken().equals("value")){
                            value = sb.nextToken();
                            nameTable = sb.nextToken();
                            // вывоз соответствующей функции удаления всех ключей с заданным значением в таблице db
                            break;
                        }
                    System.out.println("ERRORdelAll");
                }
                nameTable = start;
                // вывоз соответствующей функции удаления таблицы db
                System.out.println("ERRORdel");
                break;
            case "download": start = "download";
                System.out.println(start);
                nameTable = sb.nextToken();
                // вывоз соответствующей функции возвращения таблицы db
                break;
            case "create": start = "create";
                System.out.println(start);
                nameTable = sb.nextToken();
                // вывоз соответствующей функции создания таблицы db
                break;
            default:
                System.out.println("DEF ERROR");
                break;
        }
        return result;
    }
    public static void main(String[] args) {
        String sql = "SELECT" ;
        NoSqlParser nsp = new NoSqlParser();
        nsp.execute(sql);

    }
}

