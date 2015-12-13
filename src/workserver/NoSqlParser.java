package workserver;

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
    public String execute(String sql,List<NoSqlDB> listDb, ResponseItem items){
        sb = new StringTokenizer(sql);
        start = sb.nextToken();
        switch (start){
            case "size" : start = "size";
                items.ResponseItemList.add(new Item("size",Long.toString(listDb.get(0).getFileSize())));
                System.out.println(listDb.get(0).getDbName());
                break;
            case "output" : start = "output";
                System.out.println(start);
                start = sb.nextToken();
                if (start.equals("value")){
                    if (sb.nextToken().equals("key"))
                        key = sb.nextToken();
                    nameTable = sb.nextToken();
                    items = listDb.get(0).getValue(key);
                    // ����� ��������������� ������� ������ �������� ����� � ������� db
                    break;
                }
                if (start.equals("all")){
                    if (sb.nextToken().equals("key") && sb.nextToken().equals("value"))
                        value = sb.nextToken();
                    nameTable = sb.nextToken();
                    items = listDb.get(0).getKeys(value);
                    // ����� ��������������� ������� ������ ���� ������ � �������� ��������� � ������� db
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
                    // ����� ���������� 1 � ������ ������, 0 � ������ �������
                    listDb.get(0).append(key,value);
                    // ����� ��������������� ������� ���������� ����� �� ��������� � ������� db
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
                    // ����� ��������������� ������� �������� ����� � ������� db
                    break;
                }
                if (start.equals("all")){
                    if (sb.nextToken().equals("key"))
                        if (sb.nextToken().equals("value")){
                            value = sb.nextToken();
                            nameTable = sb.nextToken();
                            listDb.get(0).delKey(key);
                            // ����� ��������������� ������� �������� ���� ������ � �������� ��������� � ������� db
                            break;
                        }
                    System.out.println("ERRORdelAll");
                }
                nameTable = start;
                listDb.remove(0);
                // ����� ��������������� ������� �������� ������� db
                System.out.println("ERRORdel");
                break;
            case "download": start = "download";
                System.out.println(start);
                nameTable = sb.nextToken();
                items = listDb.get(0).getAll();
                // ����� ��������������� ������� ����������� ������� db
                break;
            case "create": start = "create";
                System.out.println(start);
                nameTable = sb.nextToken();
                listDb.add(new NoSqlDB(nameTable));
                // ����� ��������������� ������� �������� ������� db
                break;
            default:
                System.out.println("DEF ERROR");
                break;
        }
        return result;
    }
    public static void main(String[] args) {

    }
}

