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
        //�������
        System.out.println("LOL0");
        //�������
        if (start.equals("create")){
            //�������
            System.out.println("LOL1");
            //�������
            nameTable = sb.nextToken();
            
            listDb.add(new NoSqlDB(nameTable));

            //�������
            System.out.println(listDb.get(0).getDbName());
            System.out.println("LOL2");
            //�������
            result = "success";
        }
        else {
            //�������
            System.out.println("LOL3");
            //�������
            result = "error";
        }
        //�������
        System.out.println("LOL4");
        //�������
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
                    // ����� ��������������� ������� ������ �������� ����� � ������� db
                    break;
                }
                if (start.equals("all")){
                    if (sb.nextToken().equals("key") && sb.nextToken().equals("value"))
                        value = sb.nextToken();
                    nameTable = sb.nextToken();
                    items = db.getKeys(value);
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
                    db.append(key, value);
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
                    db.delKey(key);
                    // ����� ��������������� ������� �������� ����� � ������� db
                    break;
                }
                if (start.equals("all")){
                    if (sb.nextToken().equals("key"))
                        if (sb.nextToken().equals("value")){
                            value = sb.nextToken();
                            nameTable = sb.nextToken();
                            db.delValue(value);
                            // ����� ��������������� ������� �������� ���� ������ � �������� ��������� � ������� db
                            break;
                        }
                    System.out.println("ERRORdelAll");
                }
                nameTable = start;
                db.deleteFile();
                db = null; // ����� �������� �������� ��� � ���� ������
                // ����� ��������������� ������� �������� ������� db
                System.out.println("ERRORdel");
                break;
            case "download": start = "download";
                System.out.println(start);
                nameTable = sb.nextToken();
                items = db.getAll();
                // ����� ��������������� ������� ����������� ������� db
                break;
            case "create": start = "create";
                System.out.println(start);
                nameTable = sb.nextToken();
                db = new NoSqlDB(nameTable);
                // ����� ��������������� ������� �������� ������� db
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

