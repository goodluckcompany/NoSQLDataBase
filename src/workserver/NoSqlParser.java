package workserver;

import java.util.StringTokenizer;

/**
 * Created by Anderson on 25.11.2015.
 */
public class NoSqlParser
{

    StringTokenizer sb;
    String result;
    String start;
    public String execute(String sql){
        sb = new StringTokenizer(sql);
        start = sb.nextToken();
        switch (start){
            case "SELECT" : start = "SELECT";
                System.out.println(start);
                break;
            case "INSERT": start = "INSERT";
                System.out.println(start);
                break;
            case "DELETE": start = "DELETE";
                System.out.println(start);
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

