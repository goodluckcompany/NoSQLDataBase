package simpleclient;

import mainserver.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** ����� ������ ��� �������� ������� <br>
  *  ���� ������: <br>
  * {@link SimpleClient#req}, {@link SimpleClient#br},{@link SimpleClient#command},
  * {@link SimpleClient#conn} <br>
  *  ������ ������: <br>
  * {@link SimpleClient#main(String[])}, {@link SimpleClient#SimpleClient()}<br>
  * @author Maslov Nikita
 */
public class SimpleClient {
    /** ��������� ������� � ��*/
    Request req;
    /** ������ � �������*/
    BufferedReader br;
    /** �������, �������� ��������*/
    String command;
    /** ���������� � ��������*/
    NoSqlConnector conn;

    /** ��������� ����� ������ {@link SimpleClient}*/
    public static void main(String[] args){
        new SimpleClient();
    }

    /** ��������� ����� ������ {@link SimpleClient} <br>
     * ��������������� ����������, ����������� ������� � �������,
     * ������������ �� ������ � ����������� �����
     * @see NoSqlConnector
     * @see Request
     */
    SimpleClient(){
        br = new BufferedReader(new InputStreamReader(System.in));
        command = "";
        conn = new NoSqlConnector("172.18.27.29");
        if(conn.establishConnection() == 0){
            System.out.println("Input command:");
            try {
                command = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(!command.equalsIgnoreCase("stop")) {
                req = conn.sendCommand(command);
                System.out.println(req.toString());
                System.out.println("Input command:");
                try {
                    command = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.breakConnection();
        }
    }


}
