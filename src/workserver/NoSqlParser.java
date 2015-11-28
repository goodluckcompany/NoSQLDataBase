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
                    // ����� ��������������� ������� ������ �������� ����� � ������� db
                    break;
                }
                if (start.equals("all")){
                    if (sb.nextToken().equals("key") && sb.nextToken().equals("value"))
                        value = sb.nextToken();
                    nameTable = sb.nextToken();
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
                            // ����� ��������������� ������� �������� ���� ������ � �������� ��������� � ������� db
                            break;
                        }
                    System.out.println("ERRORdelAll");
                }
                nameTable = start;
                // ����� ��������������� ������� �������� ������� db
                System.out.println("ERRORdel");
                break;
            case "download": start = "download";
                System.out.println(start);
                nameTable = sb.nextToken();
                // ����� ��������������� ������� ����������� ������� db
                break;
            case "create": start = "create";
                System.out.println(start);
                nameTable = sb.nextToken();
                // ����� ��������������� ������� �������� ������� db
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

